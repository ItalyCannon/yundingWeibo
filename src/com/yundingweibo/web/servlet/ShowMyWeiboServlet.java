package com.yundingweibo.web.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.yundingweibo.domain.User;
import com.yundingweibo.domain.Weibo;
import com.yundingweibo.service.WeiboService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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
        List<Weibo> list = new WeiboService().getWeiboByUserId(u.getUserId());
        PropertyFilter propertyFilter = (o, s, o1) -> {
            if ("createTime".equals(s)) {
                return false;
            }
            if ("origin".equals(s)) {
                return false;
            }
            return true;
        };
        String json = JSON.toJSONString(list, propertyFilter);
        response.getWriter().write(json);
    }
}
