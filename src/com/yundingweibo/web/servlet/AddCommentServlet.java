package com.yundingweibo.web.servlet;

import com.alibaba.fastjson.JSON;
import com.yundingweibo.domain.Comment;
import com.yundingweibo.domain.User;
import com.yundingweibo.domain.Weibo;
import com.yundingweibo.service.WeiboService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddCommentServlet", urlPatterns = "/AddCommentServlet")
public class AddCommentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        User sessionUser = (User) request.getSession().getAttribute("sessionUser");
        Weibo weibo = (Weibo) JSON.parse(request.getParameter("weibo"));
        Comment comment = (Comment) JSON.parse("comment");

        new WeiboService().addComment(weibo, comment, sessionUser);
    }
}
