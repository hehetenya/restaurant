package com.epam.koval.restaurant.servlets;

import com.epam.koval.restaurant.database.CartManager;
import com.epam.koval.restaurant.database.entity.Dish;
import com.epam.koval.restaurant.database.entity.User;
import com.epam.koval.restaurant.exeptions.AppException;
import com.epam.koval.restaurant.exeptions.DBException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(CartServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Map<Dish, Integer> cart;
        User user = (User) session.getAttribute("user");
        try{
            cart = CartManager.getCart(user.getId());
            int total = 0;
            for (Dish d: cart.keySet()) {
                total += d.getPrice() * cart.get(d);
            }
            session.setAttribute("cart", cart);
            session.setAttribute("total", total);
        }catch (DBException ex){
            log.error("In cart servlet doGet()", ex);
            throw new AppException(ex);
        }
        request.getRequestDispatcher("/WEB-INF/jsp/cart.jsp").forward(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int dishId = Integer.parseInt(request.getParameter("id"));
        int amount = Integer.parseInt(request.getParameter("amount"));
        log.trace("dish id == " + dishId + " amount = " + amount);
        try{
             if(amount > 0){
                CartManager.changeAmountOfDish(user.getId(), dishId, amount);
            } else{
                CartManager.deleteDishFromCart(user.getId(), dishId);
            }
        }catch (DBException ex){
            log.error("In cart servlet doPost() ", ex);
            throw new AppException(ex);
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}
