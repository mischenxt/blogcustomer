package dao;

import entity.Tag;
import util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理所有对标签类的数据库操作
 *
 * @author Tedu
 */
public class TagDao {
    // 封装tag实体
    private Tag mapTag(ResultSet rs) throws SQLException {
        Tag t = null;
        int oId = rs.getInt(1);
        int tagPublishedRefCount = rs.getInt(2);
        int tagReferenceCount = rs.getInt(3);
        String tagTitle = rs.getString(4);
        t = new Tag(oId, tagPublishedRefCount, tagReferenceCount, tagTitle);
        return t;
    }

    // 查询出某一篇文章的所有标签
    public List<Tag> getTagsByArticleId(int aid) {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "bt.oId,bt.tagPublishedRefCount,bt.tagReferenceCount,bt.tagTitle "
                    + "FROM  "
                    + "blogs_tag_article bta  INNER JOIN  blogs_tag bt "
                    + "ON "
                    + "bt.oid=bta.tag_oid  "
                    + "WHERE "
                    + "bta.article_oid=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, aid);
            ResultSet rs = ps.executeQuery();
            List<Tag> list = new ArrayList<Tag>();
            while (rs.next()) {
                Tag t = mapTag(rs);
                list.add(t);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // 将所有标签封装到map中的方法
    public Map<String, Integer> getTagsMap() {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "oid,tagtitle "
                    + "FROM "
                    + "blogs_tag";
            Map<String, Integer> map = new HashMap<String, Integer>();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                map.put(rs.getString(2), rs.getInt(1));
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    //查询一篇爬虫文章对应的标签
    public List<Tag> getTagsBySmartId(int sid) {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "bt.oId,bt.tagPublishedRefCount,bt.tagReferenceCount,bt.tagTitle "
                    + "FROM  "
                    + "blogs_tag_smart_article bta  INNER JOIN  blogs_tag bt "
                    + "ON "
                    + "bt.oid=bta.tag_oid  "
                    + "WHERE "
                    + "bta.smart_oid=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, sid);
            ResultSet rs = ps.executeQuery();
            List<Tag> list = new ArrayList<Tag>();
            while (rs.next()) {
                Tag t = mapTag(rs);
                list.add(t);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // 主页标签分类显示
    public List<Tag> getTopTag() {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "oId,tagPublishedRefCount,tagReferenceCount,tagTitle "
                    + "FROM "
                    + "blogs_tag "
                    + "ORDER BY "
                    + "tagReferenceCount DESC "
                    + "LIMIT 5";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Tag> list = new ArrayList<Tag>();
            while (rs.next()) {
                Tag t = mapTag(rs);
                list.add(t);
            }
            return list;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


}
