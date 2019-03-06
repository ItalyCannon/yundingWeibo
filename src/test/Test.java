package test;

import com.yundingweibo.domain.User;
import com.yundingweibo.domain.Weibo;
import com.yundingweibo.service.UserService;

/**
 * @author 杜奕明
 * @date 2019/2/24 12:33
 */
public class Test {
    public static void main(String[] args) throws Exception {
        User user = new User();
        Weibo weibo = new Weibo();
        user.setUserId(1);
        weibo.setWeiboId(2);
        new UserService().addCollection(user, weibo);
    }
}
