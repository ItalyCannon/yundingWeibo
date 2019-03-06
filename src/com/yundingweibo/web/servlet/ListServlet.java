package com.yundingweibo.web.servlet;

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
 * 测试通过，本功能不需要session
 *
 * @author 杜奕明
 * @date 2019/3/1 20:32
 */
@WebServlet(name = "ListServlet", urlPatterns = "/ListServlet")
public class ListServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String type = request.getParameter("type");
        if (type == null) {
            throw new RuntimeException("必须传递type参数");
        }
        if (!"praise".equals(type) && !"repost".equals(type)) {
            throw new RuntimeException("type必须是praise或repost");
        }
        List<Weibo> list = new WeiboService().showList(type);
        request.getSession().setAttribute("list", list);

//        SimplePropertyPreFilter filter = new SimplePropertyPreFilter(Weibo.class,
//                "weiboContent", "praiseNum", "repostNum");
//        String json = JSON.toJSONString(list, filter);
//        response.getWriter().write(json);
    }
}
