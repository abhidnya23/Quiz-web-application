package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

import dao.DBConnection;

@WebServlet("/checkExamEligibility")
public class CheckExamEligibilityServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("student_id") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }
        response.setContentType("text/html; charset=UTF-8");
        int student_id = (int) session.getAttribute("student_id");

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT COUNT(*) FROM applications WHERE student_id=? AND status='SELECTED'";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, student_id);

            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {

                // ✅ Eligible → redirect to exam
                response.sendRedirect(request.getContextPath() + "/startExam");

            } else {

                // ❌ Not eligible
                response.getWriter().println(
                    "<h3 style='color:red;'>You are not eligible for exam ❌</h3>" +
                    "<a href='jsp/dashboard.jsp'>Go Back</a>"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}