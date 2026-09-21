package controller;

import dao.DBConnection;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

@WebServlet("/saveAnswer")
public class SaveAnswerServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("attempt_id") == null) {
            response.setStatus(401);
            return;
        }

        int attempt_id = (Integer) session.getAttribute("attempt_id");
        int question_id = Integer.parseInt(request.getParameter("question_id"));
        int option_id = Integer.parseInt(request.getParameter("option_id"));

        try (Connection con = DBConnection.getConnection()) {

            // Check time validity
            PreparedStatement check = con.prepareStatement(
                "SELECT e.end_time FROM exam_attempts ea " +
                "JOIN exams e ON ea.exam_id=e.exam_id WHERE ea.attempt_id=?");
            check.setInt(1, attempt_id);

            ResultSet rs = check.executeQuery();

            if (rs.next()) {
                Timestamp end = rs.getTimestamp("end_time");

                if (new Timestamp(System.currentTimeMillis()).after(end)) {
                    response.setStatus(403);
                    return;
                }
            }

            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO answers(attempt_id, question_id, selected_option) " +
                "VALUES(?,?,?) ON DUPLICATE KEY UPDATE selected_option=?");

            ps.setInt(1, attempt_id);
            ps.setInt(2, question_id);
            ps.setInt(3, option_id);
            ps.setInt(4, option_id);

            ps.executeUpdate();

            response.getWriter().write("saved");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}