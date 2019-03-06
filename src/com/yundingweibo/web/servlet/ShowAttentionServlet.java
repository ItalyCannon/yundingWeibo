package com.yundingweibo.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.yundingweibo.domain.User;
import com.yundingweibo.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 测试通过，未测试session
 *
 * @author 杜奕明
 * @date 2019/3/1 20:12
 */
@WebServlet(name = "ShowAttentionServlet", urlPatterns = "/ShowAttentionServlet")
public class ShowAttentionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        User user = (User) request.getSession().getAttribute("sessionUser");
        int t;

        String para = request.getParameter("type");
        if (para != null) {
            t = Integer.parseInt(para);
        } else {
            throw new RuntimeException("参数不能为空");
        }

        // 1为按照关注时间降序，2为按照昵称首字母排序
        if (t != 1 && t != 2) {
            throw new RuntimeException("type必须为1或2");
        }

        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(User.class,
                "userId", "nickname", "signature", "profilePicture", "attentionGroup");
        String json = JSON.toJSONString(new UserService().showUserAttention(user, t), filter);
        response.getWriter().write(json);
    }
}
