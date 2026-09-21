package dao;

import java.sql.*;
import java.util.*;
import model.Application;

public class ApplicationDAO {
	 public boolean applyInternship(int student_id, int internship_id) {

	        try {
	            Connection con = DBConnection.getConnection();

	            // 🔍 CHECK ALREADY APPLIED
	            String check = "SELECT * FROM applications WHERE student_id=? AND internship_id=?";
	            PreparedStatement ps = con.prepareStatement(check);
	            ps.setInt(1, student_id);
	            ps.setInt(2, internship_id);

	            ResultSet rs = ps.executeQuery();

	            if (rs.next()) {
	                return false; 
	            }

	            // ✅ INSERT
	            String insert = "INSERT INTO applications(student_id, internship_id) VALUES(?, ?)";
	            PreparedStatement ps2 = con.prepareStatement(insert);
	            ps2.setInt(1, student_id);
	            ps2.setInt(2, internship_id);

	            ps2.executeUpdate();
	            return true;

	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return false;
	    }
	 
	 public boolean updateStatus(int application_id, String status) {

		    try {
		        Connection con = DBConnection.getConnection();

		        String query = "UPDATE applications SET status=? WHERE application_id=?";

		        PreparedStatement ps = con.prepareStatement(query);
		        ps.setString(1, status);
		        ps.setInt(2, application_id);

		        int rows = ps.executeUpdate();

		        return rows > 0;

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return false;
		}
	 
	 public List<Application> getApplicationsByStudent(int student_id) {

		    List<Application> list = new ArrayList<>();

		    try {
		        Connection con = DBConnection.getConnection();

		        String query =
		            "SELECT a.application_id, a.student_id, a.status, a.applied_date, " +
		            "i.role AS internship_role, c.company_name, c.location, " +
		            "IFNULL(SUM(ans.marks_awarded),0) AS score " +
		            "FROM applications a " +
		            "JOIN internships i ON a.internship_id = i.internship_id " +
		            "JOIN companies c ON i.company_id = c.company_id " +
		            "LEFT JOIN exam_attempts ea ON ea.user_id = a.student_id AND ea.status='SUBMITTED' " +
		            "LEFT JOIN answers ans ON ea.attempt_id = ans.attempt_id " +
		            "WHERE a.student_id = ? " +
		            "GROUP BY a.application_id";

		        PreparedStatement ps = con.prepareStatement(query);
		        ps.setInt(1, student_id);

		        ResultSet rs = ps.executeQuery();

		        while (rs.next()) {

		            Application app = new Application();

		            app.setApplication_id(rs.getInt("application_id"));
		            app.setStudent_id(rs.getInt("student_id"));
		            app.setStatus(rs.getString("status"));
		            app.setApplied_date(rs.getString("applied_date"));

		            app.setInternship_role(rs.getString("internship_role"));
		            app.setCompany_name(rs.getString("company_name"));
		            app.setLocation(rs.getString("location"));

		            app.setScore(rs.getDouble("score"));   // 🔥 IMPORTANT

		            list.add(app);
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return list;
		}
	 public List<Application> getAllApplications() {

		    List<Application> list = new ArrayList<>();

		    try {
		        Connection con = DBConnection.getConnection();

		        String query = 
		        	    "SELECT a.application_id, a.student_id, u.name, s.cgpa, a.status, " +
		        	    "i.role AS internship_role, c.company_name, c.location, " +

		        	    "IFNULL(SUM(ans.marks_awarded),0) AS score " +   // ✅ FIXED

		        	    "FROM applications a " +

		        	    "JOIN users u ON a.student_id = u.user_id " +
		        	    "LEFT JOIN students s ON s.user_id = u.user_id " +

		        	    "JOIN internships i ON a.internship_id = i.internship_id " +
		        	    "JOIN companies c ON i.company_id = c.company_id " +

		        	    "LEFT JOIN exam_attempts ea " +
		        	    "ON ea.user_id = a.student_id AND ea.status='SUBMITTED' " +

		        	    "LEFT JOIN answers ans ON ea.attempt_id = ans.attempt_id " +

		        	    "GROUP BY a.application_id";

		        PreparedStatement ps = con.prepareStatement(query);
		        ResultSet rs = ps.executeQuery();

		        while (rs.next()) {

		            Application app = new Application();

		            app.setApplication_id(rs.getInt("application_id"));
		            app.setStudent_id(rs.getInt("student_id"));
		            app.setStudent_name(rs.getString("name"));   // 🔥 NEW
		            app.setStatus(rs.getString("status"));
		            app.setCgpa(rs.getDouble("cgpa")); 
		            app.setInternship_role(rs.getString("internship_role")); 
		            app.setCompany_name(rs.getString("company_name"));
		            app.setLocation(rs.getString("location"));

		            app.setScore(rs.getDouble("score"));         // 🔥 NEW

		            list.add(app);
		        }

		    } catch (Exception e) {
		        e.printStackTrace();
		    }

		    return list;
		}
	 
	 
}
