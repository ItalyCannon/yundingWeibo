package com.yundingweibo.web.servlet.unchanged;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Description:
 *
 * @author 关栋伟
 * @date 2019/02/15
 */
@WebServlet("/failServlet")
public class FailServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //给页面写一句话
        //设置编码
        response.setContentType("text/html;charset=utf-8");
        //输出提示语句
        response.getWriter().write("确认不了眼神，你不是要登录的人");

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
