package com.yundingweibo.dao.impl;

import com.yundingweibo.dao.RootDao;
import com.yundingweibo.dao.impl.daoutil.DaoUtil;
import com.yundingweibo.domain.Root;
import com.yundingweibo.domain.User;
import com.yundingweibo.domain.Weibo;

import java.util.List;

/**
 * Description:
 *
 * @author 关栋伟
 * @date 2019/03/26
 */
public class JdbcRootDaoImpl implements RootDao {
    @Override
    public Root findRootByLoginInId(String loginId) {
	   String sql = "select * from admin_info where login_id=?";
	   return DaoUtil.toBeanSingle(Root.class, sql, loginId);
    }

    @Override
    public List<User> findUsersByLoginInIdOrNickname(String loginId, String nickname) {
	   String sql = "select * from user_info where user_id like '%?%' or nickname like '%?%' order by registration desc";
        return DaoUtil.toBean(User.class, sql, loginId, nickname);
    }


    @Override
    public List<Weibo> getWeiboById(int loginId) {
	   String sql = "select * from weibo_data where user_id in(select user_id from user_info where login_id=?) order by create_time desc";
        return DaoUtil.toBean(Weibo.class, sql, loginId);
    }

    @Override
    public List<Weibo> getWeiboByname(String nickname) {
	   String sql = "select * from weibo_data where user_id in(select user_id from user_info where nickname=?) order by create_time desc";
        return DaoUtil.toBean(Weibo.class, sql,nickname);
    }
}
