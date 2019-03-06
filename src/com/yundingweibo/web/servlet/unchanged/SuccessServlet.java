package com.yundingweibo.web.servlet.unchanged;

import com.yundingweibo.domain.User;

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
@WebServlet("/successServlet")
public class SuccessServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取共享域中的user对象
        User user = (User) request.getAttribute("user");
        //防止空指针异常
        if (user != null) {
            //给页面写一句话
            //设置编码
            response.setContentType("text/html;charset=utf-8");
            //输出提示语句
            response.getWriter().write("确认过眼神，  你是成功登录的人！");
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
