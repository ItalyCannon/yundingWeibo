package com.yundingweibo.web.servlet;

import com.alibaba.fastjson.JSON;
import com.yundingweibo.domain.Comment;
import com.yundingweibo.domain.ReplyComment;
import com.yundingweibo.domain.User;
import com.yundingweibo.service.WeiboService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "PraiseServlet", urlPatterns = "/PraiseServlet")
public class PraiseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String param = request.getParameter("type");
        WeiboService weiboService = new WeiboService();
        User sessionUser = (User) request.getSession().getAttribute("sessionUser");
        sessionUser = new User(1);
        switch (param) {
            case "weibo":
                String weibo = request.getParameter("weibo");
                int parse = (int) JSON.parse(weibo);
                try {
                    weiboService.praiseWeibo(parse, sessionUser);
                } catch (Exception e) {
                    response.getWriter().write(JSON.toJSONString(e.getMessage()));
                }
                break;
            case "comment":
                String comment = request.getParameter("comment");
                Comment parse1 = (Comment) JSON.parse(comment);
                weiboService.praiseComment(parse1, sessionUser);
                break;
            case "reply":
                String reply = request.getParameter("reply");
                ReplyComment parse2 = (ReplyComment) JSON.parse(reply);
                weiboService.praiseReply(parse2, sessionUser);
                break;
            default:
                throw new RuntimeException("type类型未知");
        }
    }
}
