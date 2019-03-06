package com.yundingweibo.web.servlet;

import com.yundingweibo.domain.User;
import com.yundingweibo.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author 关栋伟
 * @date 2019/3/3 10:29
 */
@WebServlet(name = "LoginInServlet", urlPatterns = "/LoginInServlet")
public class LoginInServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        //调用UserDao的login方法
        //获取参数
        //获取请求参数
        String loginId = request.getParameter("loginId");
        String password = request.getParameter("password");
        String checkCode = request.getParameter("checkCode");

        //获取生成的验证码
        HttpSession session = request.getSession();
        String checkCode_session = (String) session.getAttribute("checkCode_session");
        //删除session中的验证码
        session.removeAttribute("checkCode_session");

        //先判断验证码是否正确
        if (checkCode_session != null && checkCode_session.equalsIgnoreCase(checkCode)) {
            //忽略大小写比较
            //验证码正确
            //判断输入的登陆账号密码是否正确
            //        //封装user对象
            User loginUser = new User();
            loginUser.setLoginId(loginId);
            loginUser.setPassword(password);
            User user = null;
            try {
                user = new UserService().login(loginUser);
            } catch (Exception e) {
                request.setAttribute("msg", e.getMessage());
                request.getRequestDispatcher("/login.jsp").forward(request, response);
                return;
            }

            //登录成功，存储数据并转发
            session.setAttribute("user", user);
            //重定向到success.jsp
            // TODO: 2019/3/3 改成首页
            response.sendRedirect(request.getContextPath() + "/success.jsp");

        } else {
            //验证码不正确
            //储存信息到request
            request.setAttribute("cc_error", "您输入的验证码有误或已失效");
            //转发到登录页面
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
