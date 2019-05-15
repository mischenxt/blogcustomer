package dao;

import entity.Article;
import entity.Tag;
import entity.User;
import util.DBUtils;
import util.PageInfo;

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
public class ArticleDao {
    TagDao td = new TagDao();

    // ��ѯʵ���װ
    public Article mapArticle(ResultSet rs) throws SQLException {
        //�����������ֵ
        int oId = rs.getInt(1);
        String articleTitle = rs.getString(2);
        String articleAbstract = rs.getString(3);
        int articleCommentCount = rs.getInt(5);
        int articleViewCount = rs.getInt(6);
        String articleContent = rs.getString(7);
        int articleHadBeenPublished = rs.getInt(8);
        int articleIsPublished = rs.getInt(9);
        int articlePutTop = rs.getInt(10);
        long articleCreated = rs.getLong(11);
        long articleUpdated = rs.getLong(12);
        int articleRandomDouble = rs.getInt(13);
        int articleCommentable = rs.getInt(14);
        //����һ�����µ�ʵ����
        Article article = new Article(oId, articleTitle, articleAbstract, articleCommentCount, articleViewCount,
                articleContent, articleHadBeenPublished, articleIsPublished, articlePutTop, articleCreated,
                articleUpdated, articleRandomDouble, articleCommentable);
        return article;
    }

    // ��id��ѯ��һƪ����,�������µı�ǩ
    public Article getArticleById(int oid) {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "a.oId,a.articleTitle,a.articleAbstract,a.articleAuthorId,a.articleCommentCount,a.articleViewCount,"
                    + "a.articleContent,a.articleHadBeenPublished,a.articleIsPublished,a.articlePutTop,a.articleCreated,"
                    + "a.articleUpdated,a.articleRandomDouble,a.articleCommentable,u.username "
                    + "FROM "
                    + "blogs_article  a INNER JOIN blogs_user u "
                    + "ON a.articleAuthorId=u.oid  "
                    + "WHERE "
                    + "a.oid=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, oid);
            ResultSet rs = ps.executeQuery();
            Article a = null;
            while (rs.next()) {
                a = mapArticle(rs);
                // ��������£���Ҫ��ѯ������µı�ǩ
                List<Tag> tags = td.getTagsByArticleId(a.getoId());
                User u = new User();
                // web��������Ϣ
                u.setUserName(rs.getString(15));
                a.setUser(u);
                a.setTags(tags);
            }
            return a;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // ��ҳ��8ƪ����
    public List<Article> getArticlesTopList() {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "a.oId,a.articleTitle,a.articleAbstract,a.articleAuthorId,a.articleCommentCount,a.articleViewCount,"
                    + "a.articleContent,a.articleHadBeenPublished,a.articleIsPublished,a.articlePutTop,a.articleCreated,"
                    + "a.articleUpdated,a.articleRandomDouble,a.articleCommentable,u.username "
                    + "FROM "
                    + "blogs_article  a INNER JOIN blogs_user u "
                    + "ON a.articleAuthorId=u.oid  "
                    + "WHERE "
                    + "articleIsPublished=1 "
                    + "ORDER BY "
                    + "articlePutTop DESC, articleCreated DESC "
                    + "LIMIT 8";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Article> list = new ArrayList<Article>();
            while (rs.next()) {
                // �������±���Ϣ
                Article a = mapArticle(rs);
                User u = new User();
                // web��������Ϣ
                u.setUserName(rs.getString(15));
                a.setUser(u);
                // ��������£���Ҫ��ѯ������µı�ǩ
                List<Tag> tags = td.getTagsByArticleId(a.getoId());
                a.setTags(tags);
                list.add(a);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // ��ѯ���·���������
    public List<Article> getArticlesNewList() {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "a.oId,a.articleTitle,a.articleAbstract,a.articleAuthorId,a.articleCommentCount,a.articleViewCount,"
                    + "a.articleContent,a.articleHadBeenPublished,a.articleIsPublished,a.articlePutTop,a.articleCreated,"
                    + "a.articleUpdated,a.articleRandomDouble,a.articleCommentable "
                    + "FROM "
                    + "blogs_article a  "
                    + "WHERE "
                    + "articleIsPublished=1 "
                    + "ORDER BY "
                    + "articleUpdated desc "
                    + "LIMIT 5";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Article> list = new ArrayList<Article>();
            while (rs.next()) {
                // �������±���Ϣ
                Article a = mapArticle(rs);
                list.add(a);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // ��ѯ������۵�����
    public List<Article> getArticlesCommentList() {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "a.oId,a.articleTitle,a.articleAbstract,a.articleAuthorId,a.articleCommentCount,a.articleViewCount,"
                    + "a.articleContent,a.articleHadBeenPublished,a.articleIsPublished,a.articlePutTop,a.articleCreated,"
                    + "a.articleUpdated,a.articleRandomDouble,a.articleCommentable "
                    + "FROM "
                    + "blogs_article a  "
                    + "WHERE "
                    + "articleIsPublished=1 "
                    + "ORDER BY "
                    + "articleCommentCount DESC "
                    + "LIMIT 5";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Article> list = new ArrayList<Article>();
            while (rs.next()) {
                // �������±���Ϣ
                Article a = mapArticle(rs);
                list.add(a);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // ��ѯ������������
    public List<Article> getArticlesViewList() {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "a.oId,a.articleTitle,a.articleAbstract,a.articleAuthorId,a.articleCommentCount,a.articleViewCount,"
                    + "a.articleContent,a.articleHadBeenPublished,a.articleIsPublished,a.articlePutTop,a.articleCreated,"
                    + "a.articleUpdated,a.articleRandomDouble,a.articleCommentable "
                    + "FROM "
                    + "blogs_article  a "
                    + "WHERE "
                    + "articleIsPublished=1 "
                    + "ORDER BY "
                    + "articleViewCount DESC "
                    + "LIMIT 5";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Article> list = new ArrayList<Article>();
            while (rs.next()) {
                // �������±���Ϣ
                Article a = mapArticle(rs);
                list.add(a);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // ���¼�1���
    public void addArticleView(int aid) {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "UPDATE "
                    + "blogs_article "
                    + "SET  "
                    + "articleViewCount=articleViewCount+1 "
                    + "WHERE oid=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, aid);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // ���¼�1����
    public void addArticleComment(int aid) {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "UPDATE "
                    + "blogs_article "
                    + "SET  "
                    + "articleCommentCount=articleCommentCount+1 "
                    + "WHERE oid=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, aid);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // ��ѯǰһƪ
    public Article getArticlePrev(int oid) {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "a.oId,a.articleTitle,a.articleAbstract,a.articleAuthorId,a.articleCommentCount,a.articleViewCount,"
                    + "a.articleContent,a.articleHadBeenPublished,a.articleIsPublished,a.articlePutTop,a.articleCreated,"
                    + "a.articleUpdated,a.articleRandomDouble,a.articleCommentable "
                    + "FROM "
                    + "blogs_article a "
                    + "WHERE "
                    + "articleIsPublished=1 AND articleCreated<"
                    + "(SELECT "
                    + "articleCreated "
                    + "FROM "
                    + "blogs_article "
                    + "WHERE oid=?)  "
                    + "ORDER BY "
                    + "articleCreated DESC "
                    + "LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, oid);
            ResultSet rs = ps.executeQuery();
            Article a = null;
            while (rs.next()) {
                // �������±���Ϣ
                a = mapArticle(rs);
            }
            return a;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // ��ѯ��һƪ
    public Article getArticleNext(int oid) {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "a.oId,a.articleTitle,a.articleAbstract,a.articleAuthorId,a.articleCommentCount,a.articleViewCount,"
                    + "a.articleContent,a.articleHadBeenPublished,a.articleIsPublished,a.articlePutTop,a.articleCreated,"
                    + "a.articleUpdated,a.articleRandomDouble,a.articleCommentable "
                    + "FROM "
                    + "blogs_article a "
                    + "WHERE "
                    + "articleIsPublished=1 AND articleCreated>"
                    + "(SELECT "
                    + "articleCreated "
                    + "FROM "
                    + "blogs_article "
                    + "WHERE oid=?)  "
                    + "ORDER BY "
                    + "articleCreated ASC "
                    + "LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, oid);
            ResultSet rs = ps.executeQuery();
            Article a = null;
            while (rs.next()) {
                // �������±���Ϣ
                a = mapArticle(rs);
            }
            return a;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // ��ѯͬ��ǩ������Ķ�
    public List<Article> getSameTagArticle(int oid) {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "DISTINCT "
                    + "a.oId,a.articleTitle,a.articleAbstract,a.articleAuthorId,a.articleCommentCount,a.articleViewCount,"
                    + "a.articleContent,a.articleHadBeenPublished,a.articleIsPublished,a.articlePutTop,a.articleCreated,"
                    + "a.articleUpdated,a.articleRandomDouble,a.articleCommentable "
                    + "FROM "
                    + "blogs_article a JOIN blogs_tag_article t "
                    + "ON a.oId=t.article_oid "
                    + "WHERE "
                    + "articleIsPublished=1 AND t.tag_oid IN"
                    + "(SELECT "
                    + "tag_oid "
                    + "FROM "
                    + "blogs_tag_article "
                    + "WHERE "
                    + "article_oid=?) "
                    + "ORDER BY "
                    + "articleUpdated DESC "
                    + "LIMIT 5";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, oid);
            ResultSet rs = ps.executeQuery();
            List<Article> list = new ArrayList<Article>();
            while (rs.next()) {
                // �������±���Ϣ
                Article a = mapArticle(rs);
                list.add(a);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // ��ѯ����Ķ�
    public List<Article> getRandomArticle() {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "DISTINCT "
                    + "alis.oid,alis.articleTitle "
                    + "FROM "
                    + "( SELECT "
                    + "1 od,"
                    + "a.oId,a.articleTitle,a.articleAbstract,a.articleAuthorId,a.articleCommentCount,a.articleViewCount,"
                    + "a.articleContent,a.articleHadBeenPublished,a.articleIsPublished,a.articlePutTop,a.articleCreated,"
                    + "a.articleUpdated,a.articleRandomDouble,a.articleCommentable "
                    + "FROM "
                    + "blogs_article a "
                    + "WHERE "
                    + "articleIsPublished=1 AND "
                    + "articleRandomDouble%FLOOR(RAND()*4)=FLOOR(RAND()*4) "
                    + "UNION  "
                    + "SELECT "
                    + "2 od,"
                    + "a.oId,a.articleTitle,a.articleAbstract,a.articleAuthorId,a.articleCommentCount,a.articleViewCount,"
                    + "a.articleContent,a.articleHadBeenPublished,a.articleIsPublished,a.articlePutTop,a.articleCreated,"
                    + "a.articleUpdated,a.articleRandomDouble,a.articleCommentable "
                    + "FROM "
                    + "blogs_article a "
                    + "WHERE articleIsPublished=1 )"
                    + "alis "
                    + "ORDER BY alis.od,alis.articleUpdated DESC "
                    + "LIMIT 5";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Article> list = new ArrayList<Article>();
            while (rs.next()) {
                // �������±���Ϣ
                Article a = new Article();
                a.setoId(rs.getInt(1));
                a.setArticleTitle(rs.getString(2));
                list.add(a);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // ��ѯĳһ����ǩ��������
    //������û���������±���ģ����ѯ����
    public List<Article> getArticlesByTag(String tagid, String title, PageInfo pi) {
        try (Connection conn = DBUtils.getConn()) {
            StringBuffer sql = new StringBuffer();
            // ��ʼ��������
            sql.append(" WHERE articleIsPublished=1");
            if (title != null && title.length() > 0) {
                sql.append(" AND articleTitle like ? ");
            }
            if (tagid != null && tagid.length() > 0) {
                sql.append(" AND ta.tag_oid=? ");
            }
            // ��ѯ����
            int count = getArticleCountByTag(tagid, title, sql.toString());
            pi.setCount(count);
            // ��ֹҳ�볬��
            if (pi.getPageNum() > pi.getTotal()) {
                pi.setPageNum(pi.getTotal());
            }
            if (pi.getPageNum() <= 0) {
                pi.setPageNum(1);
            }
            // ������ҳ��ѯ���
            StringBuffer sqlsel = new StringBuffer(
                    "SELECT "
                            + "DISTINCT "
                            + "a.oId,a.articleTitle,a.articleAbstract,a.articleAuthorId,a.articleCommentCount,a.articleViewCount,"
                            + "a.articleContent,a.articleHadBeenPublished,a.articleIsPublished,a.articlePutTop,a.articleCreated,"
                            + "a.articleUpdated,a.articleRandomDouble,a.articleCommentable,u.username "
                            + "FROM "
                            + "blogs_article a INNER JOIN blogs_user u "
                            + "ON a.articleAuthorId=u.oid "
                            + "JOIN blogs_tag_article ta "
                            + "ON a.oId=ta.article_oId "
                            + sql.toString()
                            + " ORDER BY "
                            + "articleCreated DESC "
                            + "limit ?,?");
            // ��ҳ��ѯ�Ĵ���
            PreparedStatement ps = conn.prepareStatement(sqlsel.toString());
            int index = 1;
            if (title != null && title.length() > 0) {
                ps.setObject(index, "%" + title + "%");
                index++;
            }
            if (tagid != null && tagid.length() > 0) {
                ps.setObject(index, tagid);
                index++;
            }
            ps.setInt(index, (pi.getPageNum() - 1) * pi.getPageSize());
            ps.setInt(index + 1, pi.getPageSize());
            ResultSet rs = ps.executeQuery();
            List<Article> list = new ArrayList<Article>();
            while (rs.next()) {
                // �������±���Ϣ
                Article a = mapArticle(rs);
                User u = new User();
                // web��������Ϣ
                u.setUserName(rs.getString(15));
                a.setUser(u);
                // ��������£���Ҫ��ѯ������µı�ǩ
                List<Tag> tags = td.getTagsByArticleId(a.getoId());
                a.setTags(tags);
                list.add(a);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    //���ݲ�ѯ������ѯ��������
    private int getArticleCountByTag(String tagid, String title, String condition) {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "COUNT(1) "
                    + "FROM "
                    + "(SELECT "
                    + "DISTINCT a.oid "
                    + "FROM "
                    + "blogs_article a JOIN blogs_user u "
                    + "ON a.articleAuthorId=u.oid JOIN blogs_tag_article ta "
                    + "ON a.oId=ta.article_oId " + condition + ") abc";
            PreparedStatement ps = conn.prepareStatement(sql);
            //�ж�����������ֵ
            int index = 1;
            if (title != null && title.length() > 0) {
                ps.setObject(index, "%" + title + "%");
                index++;
            }
            if (tagid != null && tagid.length() > 0) {
                ps.setObject(index, tagid);
            }
            ResultSet rs = ps.executeQuery();
            //��ѯ������������
            int count = 0;
            while (rs.next()) {
                count = rs.getInt(1);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
