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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "MyPraiseServlet", urlPatterns = "/MyPraiseServlet")
public class MyPraiseServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String c = request.getParameter("c");
        User user = (User) request.getSession().getAttribute("sessionUser");
        int pageCode = getPageCode(request);
        int pageSize = 6;
        PageBean<Weibo> pb = null;
        if (c == null) {
            pb = new WeiboService().findPraise(user, pageCode, pageSize);
            String json = JSON.toJSONString(pb);
            response.getWriter().write(json);
        } else {
            WeiboService weiboService = new WeiboService();
            pb = weiboService.findPraise(user, 1, 10);
            List<Weibo> weiboList = new ArrayList<>();
            int totalPage = pb.getTotalPage();
            for (int i = 1; i <= totalPage; ++i) {
                weiboList.addAll(pb.getBeanList());
                pb = weiboService.findPraise(user, i + 1, 10);
            }
            String json = JSON.toJSONString(weiboList);
            response.getWriter().write(json);

        }

    }

    private int getPageCode(HttpServletRequest request) {
        String value = request.getParameter("pc");
        if (value == null || value.trim().isEmpty()) {
            return 1;
        }
        return Integer.parseInt(value);
    }
}
