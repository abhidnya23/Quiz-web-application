package dao;

import java.sql.*;
import java.util.*;

import model.Internship;

public class InternshipDAO {

    public List<Internship> getAllInternships() {

        List<Internship> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT i.internship_id, i.role, i.stipend, i.deadline, "
                         + "c.company_name, c.location, c.eligibility_cgpa "
                         + "FROM internships i "
                         + "JOIN companies c ON i.company_id = c.company_id";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Internship i = new Internship();

                i.setInternship_id(rs.getInt("internship_id"));
                i.setRole(rs.getString("role"));
                i.setStipend(rs.getDouble("stipend"));
                i.setDeadline(rs.getDate("deadline")); // ✅ FIXED

                i.setCompany_name(rs.getString("company_name"));
                i.setLocation(rs.getString("location"));

                i.setEligibility_cgpa(rs.getDouble("eligibility_cgpa")); // ✅ FIXED

                list.add(i);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
    public void addInternship(Internship i) {

        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO internships(company_id, role, stipend, deadline) VALUES (?, ?, ?, ?)";

            PreparedStatement ps = con.prepareStatement(sql);

            ps.setInt(1, i.getCompany_id());
            ps.setString(2, i.getRole());
            ps.setDouble(3, i.getStipend());
            ps.setDate(4, i.getDeadline());

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}