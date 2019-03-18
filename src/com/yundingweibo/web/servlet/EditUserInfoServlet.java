package com.yundingweibo.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.yundingweibo.domain.User;
import com.yundingweibo.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 建议使用post请求发送数据
 * 编辑个人主页，把发来的json串写入数据库
 * json串经过测试没有问题，写入数据库未测试
 *
 * @author 杜奕明
 * @date 2019/3/1 12:44
 */
@WebServlet(name = "EditUserInfoServlet", urlPatterns = "/EditUserInfoServlet")
public class EditUserInfoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");

        String json = request.getParameter("user");
        if (json == null) {
            return;
        }

        User u = JSON.parseObject(json, User.class, Feature.AllowISO8601DateFormat);
        new UserService().update(u);
        response.getWriter().write("");
    }
}
