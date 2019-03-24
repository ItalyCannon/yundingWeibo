package com.yundingweibo.web.servlet;

import com.yundingweibo.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 退出登录
 *
 * @author 杜奕明
 * @date 2019/3/24 17:31
 */
@WebServlet(name = "QuitServlet", urlPatterns = "/QuitServlet")
public class QuitServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        User sessionUser = (User) request.getSession().getAttribute("sessionUser");
        if (sessionUser != null) {
            request.getSession().removeAttribute("sessionUser");
        }
    }
}
