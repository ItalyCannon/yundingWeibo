package com.yundingweibo.web.servlet;

import com.yundingweibo.domain.User;
import com.yundingweibo.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "BackgroundServlet", urlPatterns = "/BackgroundServlet")
public class BackgroundServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        User sessionUser = (User) request.getSession().getAttribute("sessionUser");
        if (sessionUser == null) {
            response.sendRedirect("/login");
            return;
        }

        String json;
        UserService userService = new UserService();
        if (sessionUser.getBackground() == null || "".equals(sessionUser.getBackground())) {
            sessionUser.setBackground(userService.findBackgroundURL(sessionUser));
        }

        String url = request.getParameter("url");
        if (url != null) {
            sessionUser.setBackground(url);
            userService.queryBackground(sessionUser);
        }

        String background = sessionUser.getBackground();
        json = "{\"img\":\"" + background + "\"}";
        response.getWriter().write(json);
    }
}
