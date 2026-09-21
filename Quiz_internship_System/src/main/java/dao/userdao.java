
package dao;

import java.sql.*;
import model.User;

public class userdao {

    public User getUser(String email, String password) {

        User user = null;

        try {
            Connection con = DBConnection.getConnection();
            
            String sql = "SELECT u.user_id, u.role, u.name, s.student_id " +
                    "FROM users u " +
                    "LEFT JOIN students s ON u.user_id = s.user_id " +
                    "WHERE u.email=? AND u.password=?";
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email.trim());
            ps.setString(2, password.trim());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setRole(rs.getString("role"));

                user.setName(rs.getString("name"));   // ✅ FIX

                int sid = rs.getInt("student_id");
                if (!rs.wasNull()) {
                    user.setStudentId(sid);
                }

            }else {
                System.out.println("Login Failed ❌");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return user;
    }
}