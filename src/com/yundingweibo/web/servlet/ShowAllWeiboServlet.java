package com.yundingweibo.web.servlet;

import com.alibaba.fastjson.JSON;
import com.yundingweibo.domain.PageBean;
import com.yundingweibo.domain.Weibo;
import com.yundingweibo.service.WeiboService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * recommend界面的微博展示，直接把数据库的所有微博按照时间降序展示
 *
 * @author 杜奕明
 * @date 2019/3/19 18:50
 */
@WebServlet(name = "ShowAllWeiboServlet", urlPatterns = "/ShowAllWeiboServlet")
public class ShowAllWeiboServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        int pageCode = getPageCode(request);
        int pageSize = 6;
        PageBean<Weibo> pb = new WeiboService().showAll(pageCode, pageSize);

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
