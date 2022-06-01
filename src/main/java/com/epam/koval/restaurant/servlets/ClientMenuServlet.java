package com.epam.koval.restaurant.servlets;

import com.epam.koval.restaurant.database.CartManager;
import com.epam.koval.restaurant.database.DishManager;
import com.epam.koval.restaurant.database.ReceiptManager;
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
import java.util.List;

@WebServlet("/menu")
public class ClientMenuServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(ClientMenuServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        String sortBy = request.getParameter("sortBy");
        if(sortBy == null) sortBy = "category";
        String curPage = request.getParameter("currentPage");
        if(curPage == null || curPage.isEmpty()) curPage = "1";
        int currentPage = Integer.parseInt(curPage);
        HttpSession session = request.getSession();
        try{
            List<Dish> dishes;
            if(category == null || category.isEmpty() || category.equalsIgnoreCase("All")){
                dishes = DishManager.getAllDishes();
            }else{
                dishes = DishManager.getDishesByCategory(category);
            }
            int maxPage = ReceiptManager.countMaxPage(dishes.size());

            log.debug("dishes size before sorting == " + dishes.size());
            dishes = DishManager.sortBy(dishes, sortBy);
            log.debug("dishes were sorted");

            log.trace("current page == " + currentPage);
            log.debug("dishes size before getDishOnPage == " + dishes.size());
            dishes = DishManager.getDishesOnPage(dishes, currentPage);

            session.setAttribute("maxPage", maxPage);
            session.setAttribute("dishes", dishes);
            request.getRequestDispatcher("/WEB-INF/jsp/client-menu.jsp").forward(request, response);
        }catch (DBException ex){
            log.error("In Client menu servlet doGet() ", ex);
            throw new AppException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        int dishId = Integer.parseInt(request.getParameter("id"));
        int amount = Integer.parseInt(request.getParameter("amount"));
        try{
            if(!CartManager.getCart(user.getId()).containsKey(DishManager.getDishByID(dishId))){
                CartManager.addDishToCart(user.getId(), dishId, amount);
            }
        }catch (DBException ex){
            log.error("In Client menu servlet doPost() ", ex);
            throw new AppException(ex);
        }
        response.sendRedirect(request.getContextPath() + "/menu");
    }
}
