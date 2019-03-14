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
 * 测试通过
 * 没有测试session
 *
 * @author 杜奕明
 * @date 2019/2/26 19:12
 */
@WebServlet(name = "ShowMyWeiboServlet", urlPatterns = "/ShowMyWeiboServlet")
public class ShowMyWeiboServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        User u = (User) request.getSession().getAttribute("sessionUser");

        int pageCode = getPageCode(request);
        int pageSize = 6;
        PageBean<Weibo> list = new WeiboService().getWeiboByUserId(u.getUserId(), pageCode, pageSize);

        String json = JSON.toJSONString(list);
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
