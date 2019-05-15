package dao;

import entity.SmartArticle;
import util.DBUtils;
import util.PageInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 处理所有对爬虫文章类的数据库操作
 *
 * @author Tedu
 */

public class SmartDao {
    TagDao td = new TagDao();

    //新增爬虫获取的文章
    public int addSmartArticle(SmartArticle sa) {
        try (Connection conn = DBUtils.getConn()) {
            //新增之前先检查文章id是否已经存在于数据库
            String sql = "SELECT "
                    + "1 "
                    + "FROM "
                    + "blogs_smart_article "
                    + "WHERE "
                    + "articleId=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sa.getArticleId());
            ResultSet rs = ps.executeQuery();
            //如果查询有结果
            while (rs.next()) {
                //终止方法
                return 0;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException();
        }
        //如果文章没有重复,那么新增这篇文章
        try (Connection conn = DBUtils.getConn()) {
            String sql = "INSERT INTO "
                    + "blogs_smart_article "
                    + "VALUES  "
                    + "(null,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, sa.getArticleId());
            ps.setString(2, sa.getTitle());
            ps.setString(3, sa.getContent());
            ps.setInt(4, (int) (Math.random() * 10000));
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            while (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException();
        }
    }

    //分页查询爬虫获取的文章
    public List<SmartArticle> getSmartArticlesByTag(PageInfo pi) {
        try (Connection conn = DBUtils.getConn()) {
            StringBuffer sql = new StringBuffer();
            // 查询条数
            int count = getSmartArticleCountByTag();
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
                            + "oid,articleId,title,content,articleRandomDouble "
                            + "FROM "
                            + "blogs_smart_article "
                            + " ORDER BY "
                            + "oid DESC "
                            + "limit ?,?");
            // 分页查询的代码
            PreparedStatement ps = conn.prepareStatement(sqlsel.toString());
            ps.setInt(1, (pi.getPageNum() - 1) * pi.getPageSize());
            ps.setInt(2, pi.getPageSize());
            ResultSet rs = ps.executeQuery();
            List<SmartArticle> list = new ArrayList<>();
            while (rs.next()) {
                // 加载文章表信息
                SmartArticle sa = new SmartArticle(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
                sa.setTags(td.getTagsBySmartId(sa.getOid()));
                list.add(sa);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    //查询爬虫获取的文章总数
    private int getSmartArticleCountByTag() {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "COUNT(1) "
                    + "FROM "
                    + "blogs_smart_article ";
            PreparedStatement ps = conn.prepareStatement(sql);
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

    //新增爬虫文章和标签的关系表
    public void addSmartTag(int smartId, int tagId) {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "INSERT INTO "
                    + "blogs_tag_smart_article "
                    + "VALUES  "
                    + "(?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, smartId);
            ps.setInt(2, tagId);
            ps.executeUpdate();
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException();
        }


    }
}
