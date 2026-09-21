package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

import dao.ApplicationDAO;
import model.Application;

@WebServlet("/myApplications")
public class StudentApplicationsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("student_id") == null) {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp");
            return;
        }

        int student_id = (int) session.getAttribute("student_id");

        ApplicationDAO dao = new ApplicationDAO();
        List<Application> list = dao.getApplicationsByStudent(student_id);

        request.setAttribute("list", list);

        request.getRequestDispatcher("/jsp/student_applications.jsp")
               .forward(request, response);
    }
}