package com.yundingweibo.dao;

import com.yundingweibo.domain.User;
import com.yundingweibo.domain.Weibo;

import java.util.List;

/**
 * @author 杜奕明
 * @date 2019/2/15 11:10
 */
public interface UserDao {
    /**
     * 根据id查询用户
     * 查询的是user_info表
     *
     * @param userId 用户id
     * @return 封装好的User对象
     */
    User getUser(int userId);

    /**
     * 把传来的User对象写入数据库的user_info表
     *
     * @param user User对象
     */
    void update(User user);

    /**
     * 根据loginId查询用户
     *
     * @param loginId .
     * @return .
     */
    User getUserByLoginId(String loginId);

    /**
     * 根据用户id查询用户昵称
     *
     * @param userId .
     * @return 昵称
     */
    String getUserNickname(int userId);

    /**
     * user收藏Weibo
     *
     * @param user  .
     * @param weibo .
     * @author 刘鑫
     * @date 2019/2/19 11:22
     */
    void addCollection(User user, Weibo weibo);

    /**
     * 删除user收藏的weibo
     *
     * @param user  .
     * @param weibo .
     * @author 刘鑫
     * @date 2019/2/19 11:23
     */
    void removeCollection(User user, Weibo weibo);

    /**
     * 显示user收藏的所有weibo
     *
     * @param user .
     * @return .
     * @author 刘鑫
     * @date 2019/2/19 11:23
     */
    List<Weibo> showUserCollections(User user);

    /**
     * 查询user关注的所有用户
     *
     * @param user .
     * @return .
     */
    List<User> showUserAttention(User user);

    /**
     * 查询user关注的粉丝
     *
     * @param user .
     * @return .
     */
    List<User> showFans(User user);

    /**
     * 根据loginId查询用户
     * 查询的是login_info表
     *
     * @param registerId .
     * @return .
     */
    User findUserByLoginInId(String registerId);

    /**
     * 把registerUser的loginId和userId同时写入login_info和user_info表
     *
     * @param registerUser .
     */
    void addUser(User registerUser);

    /**
     * sessionUser取关removeUser
     *
     * @param sessionUser .
     * @param removeUsere .
     */
    void removeAttention(User sessionUser, User removeUsere);

    /**
     * 显示user的所有粉丝
     *
     * @param user .
     * @return .
     */
    Integer showFansNum(User user);

    /**
     * 在我的关注页面的左上角显示个人数据
     *
     * @param user .
     * @return .
     */
    User showBasicInfo(User user);

    /**
     * sessionUser关注targetUser
     *
     * @param sessionUser .
     * @param targetUser  .
     */
    void addAttention(User sessionUser, User targetUser);

    /**
     * 修改sessionUser的背景图
     *
     * @param sessionUser .
     */
    void queryBackground(User sessionUser);

    /**
     * 查询sessionUser的背景图
     *
     * @param sessionUser .
     * @return .
     */
    String findBackgroundURL(User sessionUser);
}
