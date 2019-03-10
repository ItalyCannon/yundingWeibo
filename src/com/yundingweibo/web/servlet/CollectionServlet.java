package com.yundingweibo.web.servlet;

import com.alibaba.fastjson.JSON;
import com.yundingweibo.domain.User;
import com.yundingweibo.domain.Weibo;
import com.yundingweibo.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CollectionServlet", urlPatterns = "/CollectionServlet")
public class CollectionServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        User sessionUser = (User) request.getSession().getAttribute("sessionUser");
        String weiboId = request.getParameter("weibo");

        Weibo weibo = null;
        if (weiboId != null) {
            weibo = new Weibo();
            weibo.setWeiboId(Integer.parseInt(weiboId));
        }
        String type = request.getParameter("type");
        if (type == null) {
            throw new RuntimeException("必须传递type");
        }
        switch (type) {
            case "add":
                new UserService().addCollection(sessionUser, weibo);
                break;
            case "delete":
                new UserService().removeCollection(sessionUser, weibo);
                break;
            case "show":
                List<Weibo> weiboList = new UserService().showUserCollections(sessionUser);
                String s = JSON.toJSONString(weiboList);
                response.getWriter().write(s);
                break;
            default:
                throw new RuntimeException("type类型未知");
        }
    }
}
