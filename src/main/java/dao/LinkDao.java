package dao;

import entity.Link;
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
public class LinkDao {
    // ���������װ��rs������link�����︳ֵ�Ĺ���
    public Link mapLink(ResultSet rs) throws SQLException {
        //�����������ֵ
        Link l = null;
        int oId = rs.getInt(1);
        String linkAddress = rs.getString(2);
        String linkDescription = rs.getString(3);
        int linkOrder = rs.getInt(4);
        String linkTitle = rs.getString(5);
        //ʵ����һ�����Ӷ���
        l = new Link(oId, linkAddress, linkDescription, linkOrder, linkTitle);
        return l;
    }

    // ��ѯǰ̨ҳ������
    public List<Link> getTopLinks() {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "oId,linkAddress,linkDescription,linkOrder,linkTitle "
                    + "FROM "
                    + "blogs_link "
                    + "ORDER BY "
                    + "linkOrder";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            List<Link> list = new ArrayList<Link>();
            while (rs.next()) {
                Link l = mapLink(rs);
                list.add(l);
            }
            return list;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
