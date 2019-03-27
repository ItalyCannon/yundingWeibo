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
        comment.setCommentId(11);
        comment.setWeiboId(1);
        Comment replyComment = new Comment();
        replyComment.setCommentContent("aaaa");
        User u = new User(1);
        new JdbcWeiboDaoImpl().addReply(comment, replyComment, u);
    }
}
