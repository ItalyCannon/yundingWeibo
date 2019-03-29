package com.yundingweibo.dao;

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
public interface RootDao {
    /**
     * 查找管理员
     *
     * @param loginId .
     * @return .
     */
    Root findRootByLoginInId(String loginId);

    /**
     * 通过手机号或者昵称查询用户
     *
     * @param loginId  .
     * @param nickname .
     * @return
     */
    List<User> findUsersByLoginInIdOrNickname(String loginId, String nickname);

    /**
     * 根据手机号查询微博
     *
     * @param loginId 手机号
     * @return 封装好的Weibo对象
     */
    List<Weibo> getWeiboById(int loginId);

    /**
     * 根据用户名查询微博
     *
     * @param nickname 用户名
     * @return 封装好的Weibo对象
     */
    List<Weibo> getWeiboByname(String nickname);
}
