package com.epam.koval.restaurant.servlets;

import com.epam.koval.restaurant.database.UserManager;
import com.epam.koval.restaurant.database.entity.User;
import com.epam.koval.restaurant.exeptions.AppException;
import com.epam.koval.restaurant.exeptions.DBException;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "SignupServlet", value = "/signup")
public class SignupServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/jsp/signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        if(login.isEmpty()|| password.isEmpty()){
            response.sendRedirect(request.getContextPath()+ "/signup");
            return;
        }
        try{
            if(UserManager.getUserByLogin(login) != null){
                request.setAttribute("err", "true");
                response.sendRedirect(request.getContextPath() + "/signup");
                return;
            }
            User user = UserManager.signUp(login, password);
            request.getSession().setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + "/menu");
        } catch (DBException ex) {
            throw new AppException(ex);
        }
    }
}
