package controller;

import dao.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/submitExam")
public class SubmitExamServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("attempt_id") == null) {
            response.getWriter().println("Session expired ❌");
            return;
        }

        int attempt_id = (Integer) session.getAttribute("attempt_id");

        try (Connection con = DBConnection.getConnection()) {

            con.setAutoCommit(false);

            // Evaluate MCQ
            PreparedStatement ps = con.prepareStatement(
                "UPDATE answers a " +
                "JOIN options o ON a.selected_option=o.option_id " +
                "JOIN questions q ON a.question_id=q.question_id " +
                "SET a.marks_awarded = CASE WHEN o.is_correct=1 THEN q.marks ELSE 0 END " +
                "WHERE a.attempt_id=?");

            ps.setInt(1, attempt_id);
            ps.executeUpdate();

            // Update attempt
            PreparedStatement ps2 = con.prepareStatement(
                "UPDATE exam_attempts SET status='SUBMITTED', end_time=NOW() WHERE attempt_id=?");

            ps2.setInt(1, attempt_id);
            ps2.executeUpdate();

            con.commit();

         // AFTER successful commit
            session.removeAttribute("attempt_id");

            // ✅ Add success message in session
            session.setAttribute("success_msg", "Test submitted successfully ✅");

            // Redirect to dashboard
            response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}