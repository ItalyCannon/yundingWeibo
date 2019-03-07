package com.yundingweibo.web.servlet;

import com.alibaba.fastjson.JSON;
import com.yundingweibo.domain.PageBean;
import com.yundingweibo.domain.User;
import com.yundingweibo.domain.Weibo;
import com.yundingweibo.service.WeiboService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 分页显示首页微博
 * 测试通过，调用时最好传递一个页码，如果不传页码就一直会显示第一页的内容
 *
 * @author 杜奕明
 * @date 2019/2/25 9:22
 */
@WebServlet(name = "HomeWeiboServlet", urlPatterns = "/HomeWeiboServlet")
public class HomeWeiboServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        User user = (User) request.getSession().getAttribute("sessionUser");
        user = new User(1);
        int pageCode = getPageCode(request);
        int pageSize = 6;
        PageBean<Weibo> pb = new WeiboService().findAll(user, pageCode, pageSize);

        String json = JSON.toJSONString(pb);
        response.getWriter().write(json);
    }

    private int getPageCode(HttpServletRequest request) {
        String value = request.getParameter("pc");
        if (value == null || value.trim().isEmpty()) {
            return 1;
        }
        return Integer.parseInt(value);
    }

}
