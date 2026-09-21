package controller;

import dao.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import model.Option;
import model.Question;

import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/exam")
public class ExamServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("attempt_id") == null) {
            response.sendRedirect("jsp/login.jsp");
            return;
        }

        int attempt_id = (Integer) session.getAttribute("attempt_id");

        List<Question> list = new ArrayList<>();
        Map<Integer, Integer> savedAnswers = new HashMap<>();
        int remainingTime = 0;
        int exam_id = 0;

        try (Connection con = DBConnection.getConnection()) {

            // 1. Get exam_id + remaining time
            PreparedStatement ps1 = con.prepareStatement(
                "SELECT e.exam_id, TIMESTAMPDIFF(SECOND, NOW(), e.end_time) AS remaining " +
                "FROM exam_attempts ea JOIN exams e ON ea.exam_id = e.exam_id " +
                "WHERE ea.attempt_id=? AND ea.status='IN_PROGRESS'");
            ps1.setInt(1, attempt_id);

            ResultSet rs1 = ps1.executeQuery();

            if (!rs1.next()) {
                response.getWriter().println("Exam expired or submitted ❌");
                return;
            }

            exam_id = rs1.getInt("exam_id");
            remainingTime = Math.max(0, rs1.getInt("remaining"));

            // 2. Fetch Questions
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM questions WHERE exam_id=?");
            ps.setInt(1, exam_id);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Question q = new Question();
                int qid = rs.getInt("question_id");

                q.setQuestion_id(qid);
                q.setQuestion_text(rs.getString("question_text"));
                q.setType(rs.getString("type"));
                q.setMarks(rs.getInt("marks"));

                // Options
                PreparedStatement ps2 = con.prepareStatement(
                    "SELECT * FROM options WHERE question_id=?");
                ps2.setInt(1, qid);

                ResultSet rs2 = ps2.executeQuery();

                List<Option> opts = new ArrayList<>();
                while (rs2.next()) {
                    Option op = new Option();
                    op.setOption_id(rs2.getInt("option_id"));
                    op.setOption_text(rs2.getString("option_text"));
                    opts.add(op);
                }

                q.setOptions(opts);
                list.add(q);
            }

            // 3. Load saved answers (RESUME)
            PreparedStatement ps3 = con.prepareStatement(
                "SELECT question_id, selected_option FROM answers WHERE attempt_id=?");
            ps3.setInt(1, attempt_id);

            ResultSet rs3 = ps3.executeQuery();

            while (rs3.next()) {
                savedAnswers.put(
                    rs3.getInt("question_id"),
                    rs3.getInt("selected_option")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("questions", list);
        request.setAttribute("savedAnswers", savedAnswers);
        request.setAttribute("remainingTime", remainingTime);

        request.getRequestDispatcher("/jsp/exam.jsp").forward(request, response);
    }
}