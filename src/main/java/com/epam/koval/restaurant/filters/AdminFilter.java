package com.epam.koval.restaurant.filters;

import com.epam.koval.restaurant.database.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter({"/","/menu", "/myOrders", "/cart"})
public class AdminFilter extends HttpFilter {

    @Override
    public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            response.sendRedirect(request.getContextPath()+ "/login");
        } else if(user.getRoleId() == 2){
            response.sendRedirect(request.getContextPath()+ "/manageOrders");
        } else{
            chain.doFilter(request, response);
        }
    }
}
