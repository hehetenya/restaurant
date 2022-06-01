package com.epam.koval.restaurant.servlets;

import com.epam.koval.restaurant.database.CartManager;
import com.epam.koval.restaurant.database.DishManager;
import com.epam.koval.restaurant.database.ReceiptManager;
import com.epam.koval.restaurant.database.entity.Dish;
import com.epam.koval.restaurant.database.entity.Receipt;
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
import java.util.Map;

@WebServlet("/myOrders")
public class ClientOrdersServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(ClientOrdersServlet.class);

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
                log.trace("current page == " + currentPage);
                log.trace("receipts == " + receipts);
                session.setAttribute("maxPage", maxPage);
            }
            session.setAttribute("receipts", receipts);
            request.getRequestDispatcher("/WEB-INF/jsp/client-orders.jsp").forward(request, response);
        } catch(DBException ex){
            log.error("In client orders servlet doGet() ", ex);
            throw new AppException(ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Map<Dish, Integer> cart = (Map<Dish, Integer>) session.getAttribute("cart");
        try{
            CartManager.submitOrder(user.getId(), cart);
            CartManager.cleanCart(user.getId());
        }catch (DBException ex){
            log.error("In client orders servlet doPost() ", ex);
            throw new AppException(ex);
        }
        response.sendRedirect(request.getContextPath() + "/myOrders");
    }
}
