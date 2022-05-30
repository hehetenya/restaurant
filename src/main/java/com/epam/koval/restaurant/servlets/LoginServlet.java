package com.epam.koval.restaurant.servlets;

import com.epam.koval.restaurant.database.UserManager;
import com.epam.koval.restaurant.database.entity.User;
import com.epam.koval.restaurant.exeptions.AppException;
import com.epam.koval.restaurant.exeptions.DBException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet({"/","/login"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if (login.isEmpty() || password.isEmpty()){
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }
        try{
            User user = UserManager.logIn(login, password);
            if(user != null){
                request.getSession().setAttribute("user", user);
                if(user.getRoleId() == 1){
                    response.sendRedirect(request.getContextPath() + "/menu");
                } else {
                    response.sendRedirect(request.getContextPath() + "/manageOrders");
                }
            } else {
                request.setAttribute("error", "true");
                response.sendRedirect(request.getContextPath() + "/login?err=");
            }
        } catch (DBException ex){
            throw new AppException("Login error", ex);
        }
    }
}
