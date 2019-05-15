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
 * �������ж�����������ݿ����
 *
 * @author Tedu
 */
public class CommentDao {
    // ��װ���۶���
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
        // ��������
        com = new Comment(oId, commentContent, commentCreated);
        // �������۵��û�
        User u = new User();
        u.setUserName(commentName);
        u.setoId(commentUserId);
        u.setUserAvatar(commentThumbnailURL);
        // ���۵�����
        Article article = new Article();
        article.setoId(commentOnId);
        // �����ǻظ����۵�
        // �ظ����۵��û�
        User uu = new User();
        uu.setUserName(commentOriginalCommentName);
        // �ظ�����
        Comment cc = new Comment();
        cc.setoId(commentOriginalCommentId);
        // ��Ϲ���
        cc.setUser(uu);
        com.setArticle(article);
        com.setUser(u);
        com.setComment(cc);
        return com;
    }

    // ��ƪ���µ���������
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

    // ����һ������
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
