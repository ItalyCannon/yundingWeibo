package com.yundingweibo.web.servlet;

import com.alibaba.fastjson.JSON;
import com.yundingweibo.domain.Comment;
import com.yundingweibo.domain.User;
import com.yundingweibo.service.WeiboService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 用于查看我的评论，包括我发出和我收到的评论
 *
 * @author 杜奕明
 * @date 2019/3/8 20:33
 */
@WebServlet(name = "MyCommentServlet", urlPatterns = "/MyCommentServlet")
public class MyCommentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        User sessionUser = (User) request.getSession().getAttribute("sessionUser");

        List<Comment> comments;
        String type = request.getParameter("type");
        switch (type) {
            case "send":
                comments = new WeiboService().showCommentAndReply(sessionUser, 1);
                break;
            case "receive":
                comments = new WeiboService().showCommentAndReply(sessionUser, 2);
                break;
            default:
                throw new RuntimeException("必须传递type");
        }

        String json = JSON.toJSONString(comments);
        response.getWriter().write(json);
    }
}
