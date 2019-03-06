package com.yundingweibo.web.servlet;

import cn.itcast.vcode.utils.VerifyCode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author 关栋伟
 * @date 2019/3/3 9:58
 */
@WebServlet(name = "VerifyCodeServlet", urlPatterns = "/VerifyCodeServlet")
public class VerifyCodeServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");

        VerifyCode vc = new VerifyCode();
        BufferedImage image = vc.getImage();
        //获取验证码文本
        request.getSession().setAttribute(name, vc.getText());
        //输出图片到页面
        VerifyCode.output(image, response.getOutputStream());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
