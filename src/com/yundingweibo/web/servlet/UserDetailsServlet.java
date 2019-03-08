package com.yundingweibo.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.yundingweibo.domain.User;
import com.yundingweibo.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 暂时没法测试
 * 把数据库里的user_info表内的信息封装成一个User对象
 *
 * @author 杜奕明
 * @date 2019/2/24
 */
@WebServlet(name = "UserDetailsServlet", urlPatterns = "/UserDetailsServlet")
public class UserDetailsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * 把数据库里的user_info表内的信息封装成一个User对象
     *
     * @param request  .
     * @param response .
     * @throws ServletException .
     * @throws IOException      .
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        User u = (User) request.getSession().getAttribute("sessionUser");
        u = new User(1);
        u.setLoginId("15635323339");
        User details = new UserService().showInfo(u);
        PropertyFilter propertyFilter = (o, s, o1) -> !"attentionGroup".equals(s);
        String s = JSON.toJSONString(details, propertyFilter);
        response.getWriter().write(s);
    }
}
