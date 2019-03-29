package com.yundingweibo.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @author 关栋伟
 * @date 2019/03/28
 */
@WebServlet("/ExitServlet")
public class ExitServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        if (request.getSession().getAttribute("sessionUser") != null) {
            request.getSession().removeAttribute("sessionUser");
            response.sendRedirect("/login");
        } else {
            response.getWriter().write("\"msg\":\"您还没登录\"");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}