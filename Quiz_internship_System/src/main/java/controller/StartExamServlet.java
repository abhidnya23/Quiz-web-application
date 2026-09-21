package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

import dao.DBConnection;

@WebServlet("/startExam")
public class StartExamServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        int user_id = (int) session.getAttribute("user_id");
        int application_id = Integer.parseInt(request.getParameter("application_id"));

        try (Connection con = DBConnection.getConnection()) {

            // 1. Check SELECTED
            PreparedStatement ps1 = con.prepareStatement(
                "SELECT status FROM applications WHERE application_id=? AND student_id=?");
            ps1.setInt(1, application_id);
            ps1.setInt(2, user_id);

            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next() || !"SHORTLISTED".equals(rs1.getString("status"))) {
                response.getWriter().println("Not Selected ❌");
                return;
            }

            // 2. Get Active Exam
            PreparedStatement ps2 = con.prepareStatement(
                "SELECT exam_id FROM exams WHERE NOW() BETWEEN start_time AND end_time LIMIT 1");
            ResultSet rs2 = ps2.executeQuery();

            if (!rs2.next()) {
                response.getWriter().println("No Active Exam ❌");
                return;
            }

            int exam_id = rs2.getInt("exam_id");

            // 3. Create attempt (ONLY HERE)
            PreparedStatement ps3 = con.prepareStatement(
                "INSERT IGNORE INTO exam_attempts(user_id, exam_id, start_time) VALUES(?,?,NOW())");
            ps3.setInt(1, user_id);
            ps3.setInt(2, exam_id);
            ps3.executeUpdate();

            // 4. Get attempt_id
            PreparedStatement ps4 = con.prepareStatement(
                "SELECT attempt_id FROM exam_attempts WHERE user_id=? AND exam_id=?");
            ps4.setInt(1, user_id);
            ps4.setInt(2, exam_id);

            ResultSet rs4 = ps4.executeQuery();

            if (rs4.next()) {
                session.setAttribute("attempt_id", rs4.getInt("attempt_id"));
            }

            response.sendRedirect(request.getContextPath() + "/exam");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}