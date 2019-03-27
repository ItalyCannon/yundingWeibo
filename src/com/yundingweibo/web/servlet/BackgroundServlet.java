package com.yundingweibo.web.servlet;

import com.yundingweibo.dao.impl.daoutil.DaoUtil;
import com.yundingweibo.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "com.yundingweibo.web.servlet.BackgroundServlet", urlPatterns = "/com.yundingweibo.web.servlet.BackgroundServlet")
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
        if (sessionUser.getBackground() == null || "".equals(sessionUser.getBackground())) {
            String sql = "select background from user_info where user_id=?";
            String background = (String) DaoUtil.getObject(sql, sessionUser.getUserId());
            sessionUser.setBackground(background);
        }

        String background = sessionUser.getBackground();
        json = "{\"img\":\"" + background + "\"}";
        response.getWriter().write(json);
    }
}
