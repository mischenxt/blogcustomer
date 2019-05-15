package dao;

import entity.User;
import util.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 处理所有对用户类的数据库操作
 *
 * @author Tedu
 */
public class UserDao {
    // 这个方法封装了rs对象往user对象里赋值的过程
    public User mapUser(ResultSet rs) throws SQLException {
        User u = null;
        int oId = rs.getInt(1);
        String userEmail = rs.getString(2);
        String userName = rs.getString(3);
        String userURL = rs.getString(4);
        String userPassword = rs.getString(5);
        String userRole = rs.getString(6);
        int userArticleCount = rs.getInt(7);
        int userPublishedArticleCount = rs.getInt(8);
        String userAvatar = rs.getString(9);
        u = new User(oId, userEmail, userName, userURL, userPassword, userRole, userArticleCount, userPublishedArticleCount, userAvatar);
        return u;
    }

    // 用户名密码登录方法
    public User login(String username, String pwd) {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "oId,userEmail,userName,userURL,userPassword,userRole,"
                    + "userArticleCount,userPublishedArticleCount,userAvatar "
                    + "FROM "
                    + "blogs_user "
                    + "WHERE "
                    + "username=? AND  userpassword=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, pwd);
            ResultSet rs = ps.executeQuery();
            User u = null;
            while (rs.next()) {
                u = mapUser(rs);
            }
            return u;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    // 用户名查重的方法
    public boolean existsUserbyName(String username) {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "SELECT "
                    + "oId "
                    + "FROM "
                    + "blogs_user "
                    + "WHERE "
                    + "username=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }


    // 注册用户的方法
    public int registerUser(User u) {
        try (Connection conn = DBUtils.getConn()) {
            String sql = "INSERT INTO "
                    + "blogs_user "
                    + "VALUES"
                    + "(NULL,?,?,'',?,?,0,0,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, u.getUserEmail());
            ps.setString(2, u.getUserName());
            ps.setString(3, u.getUserPassword());
            ps.setString(4, u.getUserRole());
            ps.setString(5, u.getUserAvatar());
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

}
