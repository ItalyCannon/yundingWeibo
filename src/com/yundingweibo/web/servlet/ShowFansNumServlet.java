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

/**
 * 得到用户的所有粉丝数
 *
 * @author 杜奕明
 * @date 2019/3/7 10:01
 */
@WebServlet(name = "ShowFansNumServlet", urlPatterns = "/ShowFansNumServlet")
public class ShowFansNumServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        User sessionUser = (User) request.getSession().getAttribute("sessionUser");
        sessionUser = new User(1);
        int fansNum = new UserService().showFansNum(sessionUser);
        String json = JSON.toJSONString(fansNum);
        response.getWriter().write(json);
    }
}
