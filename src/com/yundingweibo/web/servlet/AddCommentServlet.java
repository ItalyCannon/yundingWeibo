package com.yundingweibo.web.servlet;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
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

        String type = request.getParameter("type");
        if (type == null) {
            throw new RuntimeException("必须传递type");
        }

        User sessionUser = (User) request.getSession().getAttribute("sessionUser");
        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        Gson gson = new Gson();
        switch (type) {
            case "comment":
                Weibo weibo = JSON.parseObject(request.getParameter("weibo"), Weibo.class);
                Comment comment = JSON.parseObject(request.getParameter("comment"), Comment.class);
                comment.setUserId(sessionUser.getUserId());
                Comment comment1 = new WeiboService().addComment(weibo, comment, sessionUser);
                response.getWriter().write(gson.toJson(comment1));
                break;
            case "reply":
                comment = JSON.parseObject(request.getParameter("comment"), Comment.class);
                Comment replyComment = JSON.parseObject(request.getParameter("reply"), Comment.class);
                replyComment.setUserId(sessionUser.getUserId());
                Comment comment2 = new WeiboService().addReply(comment, replyComment, sessionUser);
                response.getWriter().write(gson.toJson(comment2));
                break;
            default:
        }
    }
}
