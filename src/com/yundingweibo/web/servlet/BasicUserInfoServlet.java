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
 * 用于在我的关注页面的左上角显示用户的头像，昵称，个签等
 *
 * @author 杜奕明
 * @date 2019/3/7 11:10
 */
@WebServlet(name = "BasicUserInfoServlet", urlPatterns = "/BasicUserInfoServlet")
public class BasicUserInfoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        User user = (User) request.getSession().getAttribute("sessionUser");
        user = new User(1);
        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter(User.class,
                "nickname", "signature", "profilePicture");
        String json = JSON.toJSONString(new UserService().showBasicInfo(user), simplePropertyPreFilter);
        response.getWriter().write(json);
    }
}
