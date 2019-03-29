package com.yundingweibo.service;

import com.yundingweibo.dao.DaoFactory;
import com.yundingweibo.domain.Root;
import com.yundingweibo.domain.User;

import java.util.List;

/**
 * Description:
 *
 * @author 关栋伟
 * @date 2019/03/26
 */
public class RootService {
    public Root rootlogin(Root root) {
	   Root u = DaoFactory.getRootDao().findRootByLoginInId(root.getLoginId());
	   if (u != null) {
		  if (u.getPassword().equals(root.getPassword())) {
			 u.setPassword("");
			 return u;
		  }
		  throw new RuntimeException("密码错误");
	   }
	   throw new RuntimeException("root不存在");
    }

    public List<User> findUsers(User tempUser) {

	   List<User> users = DaoFactory.getRootDao().findUsersByLoginInIdOrNickname(tempUser.getLoginId(),tempUser.getNickname());
	   if (users != null) {
			 return users;
		  }
	   throw new RuntimeException("您查询的用户不存在");
    }
}
