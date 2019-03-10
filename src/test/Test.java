package test;

import com.yundingweibo.domain.PageBean;
import com.yundingweibo.domain.User;
import com.yundingweibo.domain.Weibo;
import com.yundingweibo.service.WeiboService;

/**
 * @author 杜奕明
 * @date 2019/2/24 12:33
 */
public class Test {
    public static void main(String[] args) {
        PageBean<Weibo> pb = new WeiboService().findAll(new User(1), 1, 6);
        System.out.println(pb);
    }
}
