package com.yundingweibo.service;

import com.yundingweibo.dao.DaoFactory;
import com.yundingweibo.dao.UserDao;
import com.yundingweibo.domain.User;
import com.yundingweibo.domain.Weibo;

import java.text.CollationKey;
import java.text.Collator;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * @author 杜奕明
 * @date 2019/2/18 17:25
 */
public class UserService {
    public void update(User user) {
        UserDao u = DaoFactory.getUserDao();
        try {
            u.update(user);
        } catch (Exception e) {
            throw new ServiceException("更新数据失败");
        }
    }

    /**
     * 根据登录id获取用户对象
     *
     * @param user .
     * @return .
     */
    public User showInfo(User user) {
        return DaoFactory.getUserDao().getUserByLoginId(user.getLoginId());
    }

    /**
     * user_id weibo_id
     *
     * @param user  .
     * @param weibo .
     */
    public void addCollection(User user, Weibo weibo) {
        DaoFactory.getUserDao().addCollection(user, weibo);
    }

    /**
     * user_id weibo_id
     *
     * @param user  .
     * @param weibo .
     */
    public void removeCollection(User user, Weibo weibo) {
        DaoFactory.getUserDao().removeCollection(user, weibo);
    }

    /**
     * user_id
     *
     * @param user .
     */
    public List<Weibo> showUserCollections(User user) {
        return DaoFactory.getUserDao().showUserCollections(user);
    }

    /**
     * 返回user关注的用户集合
     *
     * @param type 1为按照关注时间降序，2为按照昵称首字母排序
     * @return 返回user关注的用户集合
     */
    public List<User> showUserAttention(User user, int type) {
        List<User> userList = DaoFactory.getUserDao().showUserAttention(user);
        if (type == 1) {
            return userList;
        }
        if (type == 2) {
            userList.sort(new Comparator<User>() {
                Collator collator = Collator.getInstance(Locale.CHINA);

                @Override
                public int compare(User o1, User o2) {
                    CollationKey k1 = collator.getCollationKey(o1.getNickname());
                    CollationKey k2 = collator.getCollationKey(o2.getNickname());
                    return k1.compareTo(k2);
                }
            });
            return userList;
        }
        throw new RuntimeException("未知形参");
    }

    public void addUser(User user) {
        UserDao userDao = DaoFactory.getUserDao();
        User u = userDao.findUserByLoginInId(user.getLoginId());

        if (u == null) {
            try {
                userDao.addUser(user);
                return;
            } catch (Exception e) {
                throw new RuntimeException("注册失败");
            }
        }
        if (u.getLoginId().equals(user.getLoginId())) {
            throw new RuntimeException("用户已存在");
        }
    }

    public User login(User user) {
        User u = DaoFactory.getUserDao().findUserByLoginInId(user.getLoginId());
        if (u != null) {
            if (u.getPassword().equals(user.getPassword())) {
                u.setPassword("");
                return u;
            }
            throw new RuntimeException("密码错误");
        }
        throw new RuntimeException("用户不存在");
    }

    public void addRepost(User user, Weibo weibo) {
        DaoFactory.getWeiboDao().addRepost(user, weibo);
    }

    public void deleteRepost(User user, Weibo weibo) {
        DaoFactory.getWeiboDao().deleteRepost(user, weibo);
    }

    public List<Weibo> getRepost(User user) {
        return DaoFactory.getWeiboDao().getRepost(user);
    }

    public void removeAttention(User sessionUser, User removeUser) {
        DaoFactory.getUserDao().removeAttention(sessionUser, removeUser);
    }

    public int showFansNum(User user) {
        return DaoFactory.getUserDao().showFansNum(user);
    }

    /**
     * 在我的关注页面的左上角显示个人数据，返回昵称，头像，个性签名
     *
     * @param user .
     * @return .
     */
    public User showBasicInfo(User user) {
        return DaoFactory.getUserDao().showBasicInfo(user);
    }
}
