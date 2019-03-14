package com.yundingweibo.web.servlet;

import com.yundingweibo.domain.User;
import com.yundingweibo.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddAttentionServlet", urlPatterns = "/AddAttentionServlet")
public class AddAttentionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        User user = (User) request.getSession().getAttribute("sessionUser");
        String userId = request.getParameter("userId");

        new UserService().addAttention(user, new User(Integer.parseInt(userId)));

        response.getWriter().write("");
    }
}
