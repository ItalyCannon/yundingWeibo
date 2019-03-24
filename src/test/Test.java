package test;

import com.yundingweibo.dao.impl.JdbcWeiboDaoImpl;
import com.yundingweibo.domain.User;
import com.yundingweibo.domain.Weibo;

/**
 * @author 杜奕明
 * @date 2019/2/24 12:33
 */
public class Test {
    public static void main(String[] args) {
        Weibo weibo = new Weibo();
        weibo.setWeiboContent("aaaaaaaaaaaaa");

        int i = new JdbcWeiboDaoImpl().addWeibo(new User(1), weibo);
        System.out.println(i);
    }
}
