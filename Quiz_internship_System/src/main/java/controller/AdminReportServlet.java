package controller;

import dao.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/adminReport")
public class AdminReportServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Map<String,Object>> companyStats = new ArrayList<>();
        List<Map<String,Object>> applicationStats = new ArrayList<>();
        List<Map<String,Object>> rankList = new ArrayList<>();
        List<Map<String,Object>> logs = new ArrayList<>();

        try(Connection con = DBConnection.getConnection()) {

            // ================= 1. Students per company =================
            String q1 = "SELECT c.company_name, COUNT(*) as count " +
                        "FROM applications a " +
                        "JOIN internships i ON a.internship_id = i.internship_id " +
                        "JOIN companies c ON i.company_id = c.company_id " +
                        "WHERE a.status='SELECTED' " +
                        "GROUP BY c.company_name";

            PreparedStatement ps1 = con.prepareStatement(q1);
            ResultSet rs1 = ps1.executeQuery();

            while(rs1.next()){
                Map<String,Object> row = new HashMap<>();
                row.put("company", rs1.getString("company_name"));
                row.put("count", rs1.getInt("count"));
                companyStats.add(row);
            }

            // ================= 2. Applications per internship =================
            String q2 = "SELECT i.role, c.company_name, COUNT(*) as count " +
                        "FROM applications a " +
                        "JOIN internships i ON a.internship_id = i.internship_id " +
                        "JOIN companies c ON i.company_id = c.company_id " +
                        "GROUP BY i.role, c.company_name";

            PreparedStatement ps2 = con.prepareStatement(q2);
            ResultSet rs2 = ps2.executeQuery();

            while(rs2.next()){
                Map<String,Object> row = new HashMap<>();
                row.put("role", rs2.getString("role"));
                row.put("company", rs2.getString("company_name"));
                row.put("count", rs2.getInt("count"));
                applicationStats.add(row);
            }

            // ================= 3. Rank List =================
            String q3 = "SELECT ea.user_id, IFNULL(SUM(a.marks_awarded),0) as marks " +
                        "FROM exam_attempts ea " +
                        "LEFT JOIN answers a ON ea.attempt_id=a.attempt_id " +
                        "GROUP BY ea.user_id " +
                        "ORDER BY marks DESC";

            PreparedStatement ps3 = con.prepareStatement(q3);
            ResultSet rs3 = ps3.executeQuery();

            while(rs3.next()){
                Map<String,Object> row = new HashMap<>();
                row.put("user_id", rs3.getInt("user_id"));
                row.put("marks", rs3.getDouble("marks"));
                rankList.add(row);
            }

            // ================= 4. Suspicious logs =================
            String q4 = "SELECT log_time, user_id, action " +
                        "FROM audit_logs ORDER BY log_time DESC LIMIT 20";

            PreparedStatement ps4 = con.prepareStatement(q4);
            ResultSet rs4 = ps4.executeQuery();

            while(rs4.next()){
                Map<String,Object> row = new HashMap<>();
                row.put("time", rs4.getTimestamp("log_time"));
                row.put("user_id", rs4.getInt("user_id"));
                row.put("action", rs4.getString("action"));
                logs.add(row);
            }

        } catch(Exception e){
            e.printStackTrace();
        }

        // 🔥 Send to JSP
        request.setAttribute("companyStats", companyStats);
        request.setAttribute("applicationStats", applicationStats);
        request.setAttribute("rankList", rankList);
        request.setAttribute("logs", logs);

        request.getRequestDispatcher("/jsp/admin_report.jsp").forward(request, response);
    }
}