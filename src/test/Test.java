package test;

import com.yundingweibo.dao.impl.JdbcWeiboDaoImpl;
import com.yundingweibo.domain.User;

/**
 * @author 杜奕明
 * @date 2019/2/24 12:33
 */
public class Test {
    public static void main(String[] args) {
        new JdbcWeiboDaoImpl().findAll(new User(1), 1, 6);
    }
}
