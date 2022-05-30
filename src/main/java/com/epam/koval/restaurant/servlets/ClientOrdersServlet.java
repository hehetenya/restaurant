package com.epam.koval.restaurant.servlets;

import com.epam.koval.restaurant.database.CartManager;
import com.epam.koval.restaurant.database.DishManager;
import com.epam.koval.restaurant.database.ReceiptManager;
import com.epam.koval.restaurant.database.entity.Dish;
import com.epam.koval.restaurant.database.entity.Receipt;
import com.epam.koval.restaurant.database.entity.User;
import com.epam.koval.restaurant.exeptions.DBException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/myOrders")
public class ClientOrdersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String curPage = request.getParameter("currentPage");
        if(curPage == null || curPage.isEmpty()) curPage = "1";
        int currentPage = Integer.parseInt(curPage);
        try{
            List<Receipt> receipts;
            receipts = ReceiptManager.getReceiptsByUserId(user.getId());
            if(receipts.size()>0){
                int maxPage = ReceiptManager.countMaxPage(receipts.size());
                receipts = ReceiptManager.getReceiptsOnPage(receipts, currentPage);
                System.out.println("current page == " + currentPage);
                System.out.println("receipts == " + receipts);
                session.setAttribute("maxPage", maxPage);
            }
            session.setAttribute("receipts", receipts);
            System.out.println(receipts);
            request.getRequestDispatcher("/WEB-INF/jsp/client-orders.jsp").forward(request, response);
        } catch(DBException ex){
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Map<Dish, Integer> cart = (Map<Dish, Integer>) session.getAttribute("cart");
        try{
            CartManager.submitOrder(user.getId(), cart);
            CartManager.cleanCart(user.getId());
        }catch (DBException ex){
            ex.printStackTrace();
        }
        //request.getRequestDispatcher(request.getContextPath() + "/myOrders").forward(request, response);
        response.sendRedirect(request.getContextPath() + "/myOrders");
    }
}
