package test;

import com.yundingweibo.dao.impl.JdbcWeiboDaoImpl;
import com.yundingweibo.domain.PageBean;
import com.yundingweibo.domain.Weibo;

/**
 * @author 杜奕明
 * @date 2019/2/24 12:33
 */
public class Test {
    public static void main(String[] args) {
        PageBean<Weibo> weiboPageBean = new JdbcWeiboDaoImpl().showAll(2, 10);
        System.out.println(weiboPageBean);
    }
}
