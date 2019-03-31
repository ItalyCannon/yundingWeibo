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

/**
 * 可以给微博，评论和回复点赞
 *
 * @author 杜奕明
 * @date 2019/3/15 17:49
 */
@WebServlet(name = "PraiseServlet", urlPatterns = "/PraiseServlet")
public class PraiseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String param = request.getParameter("type");
        WeiboService weiboService = new WeiboService();
        User sessionUser = (User) request.getSession().getAttribute("sessionUser");
        if (sessionUser == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return;
        }

        switch (param) {
            case "weibo":
                String weibo = request.getParameter("weibo");
                int parse = (int) JSON.parse(weibo);
                try {
                    int i = weiboService.praiseWeibo(parse, sessionUser);

                    response.getWriter().write("{\"msg\":" + i + "}");
                } catch (Exception e) {
                    response.getWriter().write(JSON.toJSONString(e.getMessage()));
                }
                break;
            case "comment":
                String comment = request.getParameter("comment");
                Comment parse1 = JSON.parseObject(comment, Comment.class);
                int i = weiboService.praiseComment(parse1, sessionUser);
                response.getWriter().write("{\"msg\":" + i + "}");

                break;
            default:
                throw new RuntimeException("type类型未知");
        }
    }
}
