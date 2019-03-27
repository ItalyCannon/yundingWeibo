package com.yundingweibo.web.servlet.unchanged;

import com.yundingweibo.utils.SandSmsUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Random;

/**
 * Description:
 *
 * @author 关栋伟
 * @date 2019/03/03
 */
@WebServlet("/sandSmsServlet")
public class SandSmsServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //System.out.println("到达");

        //设置编码
        request.setCharacterEncoding("utf-8");
        //获取手机号码
        String callphone = request.getParameter("loginId");
        if (callphone == null || "".equals(callphone)) {
            return;
        }
        //发送短信验证码
        //指定模板单发
        String sid = "084e8dcdeffa72d00dc44cc6958a65bc";
        String token = "20a85955cd79280f8b832c8372afcaa8";
        String appid = "82cb8f2c69054a5eaaac4d30788e320c";
        //模板id
        String templateid = "437729";

        //生成sms
        String str = "1234567890";
        Random ran = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i <= 6; i++) {
            //生成随即角标
            int index = ran.nextInt(str.length());
            //获取字符
            char ch = str.charAt(index);
            sb.append(ch);
            //写验证码
        }
        String sms_session = sb.toString();
        //将sms验证码存入session
        request.getSession().setAttribute("sms_session", sms_session);
        System.out.println("sms :" + sms_session);

        String param = sms_session;

        String mobile = callphone;

        System.out.println("callphone :" + callphone);

        String uid = "";
        try {

            SandSmsUtils.testSendSms(sid, token, appid, templateid, param, mobile, uid);

            System.out.println("send successful");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("send fail");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
