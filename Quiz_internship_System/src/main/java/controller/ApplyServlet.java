package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;

import dao.ApplicationDAO;
import dao.DBConnection;

@WebServlet("/apply")
public class ApplyServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int internship_id = Integer.parseInt(request.getParameter("internship_id"));

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("student_id") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        int student_id = (int) session.getAttribute("student_id");

        try {
            Connection con = DBConnection.getConnection();

            String query = "SELECT s.cgpa, c.eligibility_cgpa " +
                           "FROM students s, internships i, companies c " +
                           "WHERE s.student_id = ? " +
                           "AND i.internship_id = ? " +
                           "AND i.company_id = c.company_id";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, student_id);
            ps.setInt(2, internship_id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                double student_cgpa = rs.getDouble("cgpa");
                double required_cgpa = rs.getDouble("eligibility_cgpa");

                if (student_cgpa < required_cgpa) {
                    response.sendRedirect(request.getContextPath() + "/internships?msg=noteligible");
                    return;
                }

                ApplicationDAO dao = new ApplicationDAO();

                if (dao.applyInternship(student_id, internship_id)) {
                    response.sendRedirect(request.getContextPath() + "/internships?msg=success");
                } else {
                    response.sendRedirect(request.getContextPath() + "/internships?msg=already");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}