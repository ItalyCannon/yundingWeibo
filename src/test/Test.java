package test;

import com.yundingweibo.dao.impl.JdbcWeiboDaoImpl;
import com.yundingweibo.domain.Comment;
import com.yundingweibo.domain.User;

/**
 * @author 杜奕明
 * @date 2019/2/24 12:33
 */
public class Test {
    public static void main(String[] args) throws Exception {
        Comment comment = new Comment();
        comment.setCommentId(1);
        User user = new User(1);
        new JdbcWeiboDaoImpl().like(1, user);
    }
}
