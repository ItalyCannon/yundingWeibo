package com.yundingweibo.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.yundingweibo.domain.User;
import com.yundingweibo.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 点击个人头像显示基本资料
 *
 * @author 杜奕明
 * @date 2019/3/29 20:51
 */
@WebServlet(name = "OtherUserBaseInfoServlet", urlPatterns = "/OtherUserBaseInfoServlet")
public class OtherUserBaseInfoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String user = request.getParameter("user");
        User user1 = new User(Integer.parseInt(user));

        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter(User.class,
                 "nickname", "gender", "birthday", "location");
        String json = JSON.toJSONString(new UserService().showBasicInfo(user1), simplePropertyPreFilter);
        response.getWriter().write(json);
    }
}