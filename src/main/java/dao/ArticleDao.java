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
 * 处理所有对文章类的数据库操作
 *
 * @author Tedu
 */
public class ArticleDao {
    TagDao td = new TagDao();

    // 查询实体封装
    public Article mapArticle(ResultSet rs) throws SQLException {
        //获得所有属性值
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
        //创建一个文章的实体类
        Article article = new Article(oId, articleTitle, articleAbstract, articleCommentCount, articleViewCount,
                articleContent, articleHadBeenPublished, articleIsPublished, articlePutTop, articleCreated,
                articleUpdated, articleRandomDouble, articleCommentable);
        return article;
    }

    // 按id查询出一篇文章,包含文章的标签
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
                // 查出了文章，还要查询这个文章的标签
                List<Tag> tags = td.getTagsByArticleId(a.getoId());
                User u = new User();
                // web的作者信息
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

    // 主页的8篇文章
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
                // 加载文章表信息
                Article a = mapArticle(rs);
                User u = new User();
                // web的作者信息
                u.setUserName(rs.getString(15));
                a.setUser(u);
                // 查出了文章，还要查询这个文章的标签
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

    // 查询最新发布的文章
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
                // 加载文章表信息
                Article a = mapArticle(rs);
                list.add(a);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // 查询最多评论的文章
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
                // 加载文章表信息
                Article a = mapArticle(rs);
                list.add(a);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // 查询最多浏览的文章
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
                // 加载文章表信息
                Article a = mapArticle(rs);
                list.add(a);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // 文章加1浏览
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

    // 文章加1评论
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

    // 查询前一篇
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
                // 加载文章表信息
                a = mapArticle(rs);
            }
            return a;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // 查询后一篇
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
                // 加载文章表信息
                a = mapArticle(rs);
            }
            return a;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // 查询同标签的相关阅读
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
                // 加载文章表信息
                Article a = mapArticle(rs);
                list.add(a);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // 查询随机阅读
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
                // 加载文章表信息
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

    // 查询某一个标签所有文章
    //或根据用户输入的文章标题模糊查询文章
    public List<Article> getArticlesByTag(String tagid, String title, PageInfo pi) {
        try (Connection conn = DBUtils.getConn()) {
            StringBuffer sql = new StringBuffer();
            // 开始构建条件
            sql.append(" WHERE articleIsPublished=1");
            if (title != null && title.length() > 0) {
                sql.append(" AND articleTitle like ? ");
            }
            if (tagid != null && tagid.length() > 0) {
                sql.append(" AND ta.tag_oid=? ");
            }
            // 查询条数
            int count = getArticleCountByTag(tagid, title, sql.toString());
            pi.setCount(count);
            // 防止页码超限
            if (pi.getPageNum() > pi.getTotal()) {
                pi.setPageNum(pi.getTotal());
            }
            if (pi.getPageNum() <= 0) {
                pi.setPageNum(1);
            }
            // 构建分页查询语句
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
            // 分页查询的代码
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
                // 加载文章表信息
                Article a = mapArticle(rs);
                User u = new User();
                // web的作者信息
                u.setUserName(rs.getString(15));
                a.setUser(u);
                // 查出了文章，还要查询这个文章的标签
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

    //根据查询条件查询文章数量
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
            //判断条件，并赋值
            int index = 1;
            if (title != null && title.length() > 0) {
                ps.setObject(index, "%" + title + "%");
                index++;
            }
            if (tagid != null && tagid.length() > 0) {
                ps.setObject(index, tagid);
            }
            ResultSet rs = ps.executeQuery();
            //查询总条数，返回
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
