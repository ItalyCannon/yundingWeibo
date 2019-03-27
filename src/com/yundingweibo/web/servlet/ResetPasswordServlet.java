package com.yundingweibo.web.servlet;

import com.yundingweibo.dao.impl.daoutil.DaoUtil;
import com.yundingweibo.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 重置登录密码
 *
 * @author 杜奕明
 * @date 2019/3/25 21:32
 */
@WebServlet(name = "ResetPasswordServlet", urlPatterns = "/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        User sessionUser = (User)request.getSession().getAttribute("sessionUser");
        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");

        if (!sessionUser.getLoginId().equals(loginId)) {
            response.getWriter().write("{\"msg\":\"当前登录用户和更改的用户不同\"}");
            return;
        }
        String sql = "update login_info set password=? where login_id=?";
        try {
            DaoUtil.query(sql, password, loginId);
        } catch (Exception e) {
            response.getWriter().write("{\"msg\":\"更改失败\"}");
            return;
        }
        response.getWriter().write("{\"msg\":\"更改成功\"}");
        response.sendRedirect("/login");
    }
}
