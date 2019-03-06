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
 * 作废
 * 注册之后自动登录
 *
 * @author 关栋伟
 * @date 2019/3/2 17:28
 */
@Deprecated
@WebServlet(name = "RegisterServlet", urlPatterns = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/x-www-forms-urlencoded;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        System.out.println("==================================================");
        /*
         * 有一个潜在的bug:可能会在login_info表中插入一条只有user_id的数据，不知道怎么触发的
         * 但是不影响使用
         * */

        String registerId = request.getParameter("loginId");
        System.out.println(registerId);
        request.setAttribute("id", registerId);

        String checkCode = request.getParameter("checkCode");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");

        if (password != null && password.equals(password2)) {

            //password两次一样

            //获取生成的验证码
            HttpSession session = request.getSession();
            String checkCodeSession = (String) session.getAttribute("checkCode_session");
            //删除session中的验证码
            session.removeAttribute("checkCode_session");

            //判断验证码是否正确
            if (checkCodeSession != null && checkCodeSession.equalsIgnoreCase(checkCode)) {
                //忽略大小写比较
                //验证码正确
                //判断输入的注册账号是否存在
                //封装user对象
                User registerUser = new User();
                try {
                    new UserService().addUser(registerUser);
                } catch (Exception e) {
                    request.setAttribute("msg", e.getMessage());
                    // TODO: 2019/3/3 注册页面
                    request.getRequestDispatcher("").forward(request, response);
                }
            } else {
                //验证码错误
                //储存信息到request
                request.setAttribute("cc_error", "您输入的验证码有误");
                //转发到注册页面
                // TODO: 2019/3/3 注册页面
                request.getRequestDispatcher("/regist.jsp").forward(request, response);
            }
        } else {
            //两次密码输入不同
            //储存信息到request
            request.setAttribute("msg", "您两次输入的密码不同");
            //转发到注册页面
            // TODO: 2019/3/3 注册页面
            request.getRequestDispatcher("/regist.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
