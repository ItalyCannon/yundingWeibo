package com.yundingweibo.web.servlet;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.yundingweibo.dao.impl.daoutil.DaoUtil;
import com.yundingweibo.domain.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Description:
 *
 * @author 关栋伟
 * @date 2019/03/28
 */
@WebServlet("/DeleteWeiboServlet")
public class DeleteWeiboServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");

        String loginId = request.getParameter("loginId");
        String weiboId = request.getParameter("weibo");
        User sessionUser = (User) request.getSession().getAttribute("sessionUser");
        if(!sessionUser.getLoginId().equals(loginId)){
            return;
        }

        DruidPooledConnection conn;
        try {
            conn = DaoUtil.beginTransaction();
            DaoUtil.query(conn,"delete from weibo_data where weibo_id=?",weiboId);
            DaoUtil.query(conn,"update user_info set weibo_num=weibo_num-1");
            DaoUtil.commitTransaction();
        } catch (SQLException e) {
            try {
                DaoUtil.rollbackTransaction();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
