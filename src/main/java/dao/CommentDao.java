package dao;

import entity.Article;
import entity.Comment;
import entity.User;
import util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理所有对评论类的数据库操作
 *
 * @author Tedu
 */
public class CommentDao {
    // 封装评论对象
    public Comment mapComment(ResultSet rs) throws SQLException {
        int oId = rs.getInt(1);
        String commentContent = rs.getString(2);
        long commentCreated = rs.getLong(3);
        String commentName = rs.getString(4);
        int commentOnId = rs.getInt(5);
        int commentUserId = rs.getInt(6);
        String commentThumbnailURL = rs.getString(7);
        int commentOriginalCommentId = rs.getInt(8);
        String commentOriginalCommentName = rs.getString(9);
        Comment com = null;
        // 本条评论
        com = new Comment(oId, commentContent, commentCreated);
        // 本条评论的用户
        User u = new User();
        u.setUserName(commentName);
        u.setoId(commentUserId);
        u.setUserAvatar(commentThumbnailURL);
        // 评论的文章
        Article article = new Article();
        article.setoId(commentOnId);
        // 以下是回复评论的
        // 回复评论的用户
        User uu = new User();
        uu.setUserName(commentOriginalCommentName);
        // 回复评论
        Comment cc = new Comment();
        cc.setoId(commentOriginalCommentId);
        // 组合过程
        cc.setUser(uu);
        com.setArticle(article);
        com.setUser(u);
        com.setComment(cc);
        return com;
    }

    // 这篇文章的所有评论
    public List<Comment> getCommentByArticleId(int aid) {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "oId,commentContent,commentCreated,commentName,commentOnId,"
                    + "commentUserId,commentThumbnailURL,commentOriginalCommentId,"
                    + "commentOriginalCommentName "
                    + "FROM "
                    + "blogs_comment "
                    + "WHERE "
                    + "commentonId=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, aid);
            ResultSet rs = ps.executeQuery();
            List<Comment> list = new ArrayList<Comment>();
            while (rs.next()) {
                Comment c = mapComment(rs);
                list.add(c);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // 新增一个评论
    public int addComment(String commentContent, String commentOnId, String reCommentId, String reCommentName,
                          User user) {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "insert into "
                    + "blogs_comment "
                    + "values"
                    + "(null,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setObject(1, commentContent);
            ps.setObject(2, System.currentTimeMillis());
            ps.setObject(3, user.getUserName());
            ps.setObject(4, commentOnId);
            ps.setObject(5, user.getoId());
            ps.setObject(6, user.getUserAvatar());
            ps.setObject(7, reCommentId);
            ps.setObject(8, reCommentName);
            int num = ps.executeUpdate();
            return num;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
