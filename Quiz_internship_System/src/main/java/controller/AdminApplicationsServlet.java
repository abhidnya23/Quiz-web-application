package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import dao.ApplicationDAO;
import java.util.*;
import model.Application;

/**
 * Servlet implementation class AdminApplicationsServlet
 */

@WebServlet("/adminApplications")
public class AdminApplicationsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ApplicationDAO dao = new ApplicationDAO();

        List<Application> list = dao.getAllApplications();
        System.out.println("Applications size: " + list.size());

        request.setAttribute("list", list);

        // ✅ CORRECT: forward to JSP
        request.getRequestDispatcher("/jsp/admin_applications.jsp")
               .forward(request, response);
    }
}
