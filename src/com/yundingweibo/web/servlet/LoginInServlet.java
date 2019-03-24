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
 * @author 关栋伟
 * @date 2019/3/3 10:29
 */
@WebServlet(name = "LoginInServlet", urlPatterns = "/LoginInServlet")
public class LoginInServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");

        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        String path = basePath + "/recommend/index.html";

        User loginUser = new User();
        loginUser.setLoginId(loginId);
        loginUser.setPassword(password);
        Temp temp = new Temp();
        User user = null;
        try {
            user = new UserService().login(loginUser);
        } catch (Exception e) {
            path = basePath + "/login/index.html";
            temp.setMsg(e.getMessage());
            temp.setPath(path);
            String json = JSON.toJSONString(temp);
            response.getWriter().write(json);
            return;
        }

        if (request.getSession().getAttribute("sessionUser") != null) {
            request.getSession().removeAttribute("sessionUser");
        }

        //登录成功，存储数据并转发
        request.getSession().setAttribute("sessionUser", user);

        temp.setPath(path);
        temp.setMsg("登录成功");
        String json = JSON.toJSONString(temp);
        response.getWriter().write(json);
    }
}

class Temp {
    private String path;
    private String msg;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
