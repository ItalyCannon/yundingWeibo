package com.yundingweibo.web.servlet;

import com.alibaba.fastjson.JSON;
import com.yundingweibo.domain.User;
import com.yundingweibo.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "RepostServlet", urlPatterns = "/RepostServlet")
public class RepostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String type = request.getParameter("type");
        if (type == null) {
            throw new RuntimeException("type不能为空");
        }
        User sessionUser = (User) request.getSession().getAttribute("sessionUser");
        int weiboId = (int) JSON.parse(request.getParameter("weibo"));

        switch (type) {
            case "add":
                //需要完整的微博数据
                new UserService().addRepost(sessionUser, weiboId);
                break;
            case "delete":
                new UserService().deleteRepost(sessionUser, weiboId);
                break;
            case "show":
                new UserService().getRepost(sessionUser);
                break;
            default:
                throw new RuntimeException("type类型未知");
        }
    }
}
