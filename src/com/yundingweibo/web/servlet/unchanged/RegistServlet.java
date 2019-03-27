package com.yundingweibo.web.servlet.unchanged;

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
 * Description:
 *
 * @author 关栋伟
 * @date 2019/02/22
 */
@WebServlet("/registServlet")
public class RegistServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //设置编码
        request.setCharacterEncoding("utf-8");
        //接收请求参数
        String registId = request.getParameter("loginId");
        request.getSession().setAttribute("id", registId);
        String checkCode = request.getParameter("checkCode");
        String sms = request.getParameter("sms");
        request.getSession().setAttribute("sms", sms);
        String password = request.getParameter("password");
        request.getSession().setAttribute("password", password);
        String password2 = request.getParameter("password2");
        request.getSession().setAttribute("password2", password2);

        //手机号格式验证
        if (registId != null && registId.matches("^(13|15|18|14|17|19)[\\d]{9}")) {
            int maxPwd = 20;
            int minPwd = 6;
            if (password.length() > maxPwd || password.length() < minPwd) {
                request.getSession().setAttribute("regist_error", "密码长度必须为6-20位");
                response.sendRedirect("/register/index.jsp");
                return;
            }

            if (password.equals(password2)) {

                //password两次一样

                //获取生成的验证码
                HttpSession session = request.getSession();
                String checkCode_session = (String) session.getAttribute("checkCode_session");

                String sms_session = (String) session.getAttribute("sms_session");
                //删除session中的验证码
                session.removeAttribute("checkCode_session");

                //判断验证码是否正确
                if (checkCode_session != null && checkCode_session.equalsIgnoreCase(checkCode)) {
                    //忽略大小写比较
                    //验证码正确
                    //判断输入的注册账号是否存在
                    if (sms_session != null && sms_session.equals(sms)) {
                        //手机验证码正确

                        //封装user对象
                        User registUser = new User();
                        try {
                            registUser.setLoginId(registId);
                            registUser.setPassword(password);
                            registUser.setProfilePicture("https://www.baidu.com/img/dong1_dd071b75788996a161c3964d450fcd8c.gif");
                            registUser.setBackground("http://imglf0.ph.126.net/JArhqEUDh35nRkxLlFua4A==/6630333291932199435.png");
                            new UserService().addUser(registUser);
                            //注册成功，存储数据
                            session.setAttribute("user", registUser);
                            //储存信息到request
                            session.setAttribute("regist_error", "您已成功注册");
                            //转发到登录页面
                            response.sendRedirect("/home/index.html");
                        } catch (Exception e) {
                            session.setAttribute("regist_error", e.getMessage());
                            response.sendRedirect("/register/index.jsp");
                        }
                    } else {
                        //验证码错误
                        session.setAttribute("regist_error", "您输入的手机短信验证码有误");
                        //转发到注册页面
                        response.sendRedirect("/register/index.jsp");
                    }

                } else {
                    //验证码错误
                    session.setAttribute("regist_error", "您输入的验证码有误");
                    //转发到注册页面
                    response.sendRedirect("/register/index.jsp");
                }
            } else {
                //两次密码输入不同
                //储存信息到request
                request.getSession().setAttribute("regist_error", "您两次输入的密码不同");
                response.sendRedirect("/register/index.jsp");
            }

        } else {
            //手机格式错误
            //储存信息到request
            request.getSession().setAttribute("regist_error", "请您输入正确的手机号");
            response.sendRedirect("/register/index.jsp");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
