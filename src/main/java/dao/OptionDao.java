package dao;

import util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * �������ж�ѡ��������ݿ����
 *
 * @author Tedu
 */
public class OptionDao {
    // ��ѯ��������
    public Map<String, String> getOptions() {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "optionName,optionValue "
                    + "FROM "
                    + "blogs_option";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            //�˱�֮�����У�����ֱ��ʹ��map����
            Map<String, String> map = new HashMap<String, String>();
            while (rs.next()) {
                String optionName = rs.getString(1);
                String optionValue = rs.getString(2);
                map.put(optionName, optionValue);
            }
            return map;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
}
