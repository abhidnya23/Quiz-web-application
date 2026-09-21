package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Application;

import java.io.*;
import java.sql.*;
import java.util.List;

import dao.ApplicationDAO;
import dao.DBConnection;

/**
 * Servlet implementation class StudentResultServlet
 */
@WebServlet("/studentResults")
public class StudentResultsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        HttpSession session = request.getSession(false);

        if(session == null || session.getAttribute("user_id") == null){
            response.sendRedirect("login.jsp");
            return;
        }

        int user_id = (Integer) session.getAttribute("user_id");

        ApplicationDAO dao = new ApplicationDAO();
        List<Application> list = dao.getApplicationsByStudent(user_id);

        request.setAttribute("list", list);

        // ✅ FIXED PATH
        request.getRequestDispatcher("/jsp/student_result.jsp").forward(request, response);
    }
}