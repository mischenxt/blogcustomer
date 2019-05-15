package dao;

import entity.SmartArticle;
import util.DBUtils;
import util.PageInfo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * �������ж���������������ݿ����
 *
 * @author Tedu
 */

public class SmartDao {
    TagDao td = new TagDao();

    //���������ȡ������
    public int addSmartArticle(SmartArticle sa) {
        try (Connection conn = DBUtils.getConn()) {
            //����֮ǰ�ȼ������id�Ƿ��Ѿ����������ݿ�
            String sql = "SELECT "
                    + "1 "
                    + "FROM "
                    + "blogs_smart_article "
                    + "WHERE "
                    + "articleId=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, sa.getArticleId());
            ResultSet rs = ps.executeQuery();
            //�����ѯ�н��
            while (rs.next()) {
                //��ֹ����
                return 0;
            }
        } catch (SQLException e) {
            //e.printStackTrace();
            throw new RuntimeException();
        }
        //�������û���ظ�,��ô������ƪ����
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

    //��ҳ��ѯ�����ȡ������
    public List<SmartArticle> getSmartArticlesByTag(PageInfo pi) {
        try (Connection conn = DBUtils.getConn()) {
            StringBuffer sql = new StringBuffer();
            // ��ѯ����
            int count = getSmartArticleCountByTag();
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
                            + "oid,articleId,title,content,articleRandomDouble "
                            + "FROM "
                            + "blogs_smart_article "
                            + " ORDER BY "
                            + "oid DESC "
                            + "limit ?,?");
            // ��ҳ��ѯ�Ĵ���
            PreparedStatement ps = conn.prepareStatement(sqlsel.toString());
            ps.setInt(1, (pi.getPageNum() - 1) * pi.getPageSize());
            ps.setInt(2, pi.getPageSize());
            ResultSet rs = ps.executeQuery();
            List<SmartArticle> list = new ArrayList<>();
            while (rs.next()) {
                // �������±���Ϣ
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

    //��ѯ�����ȡ����������
    private int getSmartArticleCountByTag() {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "COUNT(1) "
                    + "FROM "
                    + "blogs_smart_article ";
            PreparedStatement ps = conn.prepareStatement(sql);
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

    //�����������ºͱ�ǩ�Ĺ�ϵ��
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
