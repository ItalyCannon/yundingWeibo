package com.yundingweibo.web.filter;

import com.yundingweibo.domain.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 需要登录后才能查看的页面就写到./user/下
 * 目前想到的不需要登录就能查看的页面是注册页面，登录页面，推荐页面
 *
 * @author 杜奕明
 * @date 2019/3/4 17:14
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = "/user/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        User u = (User) request.getSession().getAttribute("sessionUser");
        if (u == null) {
            response.getWriter().print("您还没有登录");
            // TODO: 2019/2/24 把这里改成登录页面路径
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
