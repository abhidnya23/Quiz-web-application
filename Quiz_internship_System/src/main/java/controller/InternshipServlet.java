package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

import dao.DBConnection;
import model.Internship;

@WebServlet("/internships")
public class InternshipServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("student_id") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        int student_id = (int) session.getAttribute("student_id");

        List<Internship> list = new ArrayList<>();
        List<Integer> appliedList = new ArrayList<>();
        double student_cgpa = 0;

        try {
            Connection con = DBConnection.getConnection();

            
            String query = "SELECT i.*, c.company_name, c.location, c.eligibility_cgpa " +
                           "FROM internships i JOIN companies c ON i.company_id = c.company_id";

            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Internship i = new Internship();

                i.setInternship_id(rs.getInt("internship_id"));
                i.setRole(rs.getString("role"));
                i.setCompany_name(rs.getString("company_name"));
                i.setLocation(rs.getString("location"));
                i.setStipend(rs.getDouble("stipend"));
                i.setDeadline(rs.getDate("deadline"));
                i.setEligibility_cgpa(rs.getDouble("eligibility_cgpa"));

                list.add(i);
            }

            
            PreparedStatement ps2 = con.prepareStatement(
                "SELECT cgpa FROM students WHERE student_id=?"
            );
            ps2.setInt(1, student_id);

            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                student_cgpa = rs2.getDouble("cgpa");
            }

           
            PreparedStatement ps3 = con.prepareStatement(
                "SELECT internship_id FROM applications WHERE student_id=?"
            );
            ps3.setInt(1, student_id);

            ResultSet rs3 = ps3.executeQuery();
            while (rs3.next()) {
                appliedList.add(rs3.getInt("internship_id"));
            }

            
            request.setAttribute("list", list);
            request.setAttribute("student_cgpa", student_cgpa);
            request.setAttribute("appliedList", appliedList);

            RequestDispatcher rd = request.getRequestDispatcher("/jsp/internship.jsp");
            rd.forward(request,response);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}