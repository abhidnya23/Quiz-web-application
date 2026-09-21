package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

import dao.ApplicationDAO;

@WebServlet("/updateStatus")
public class UpdateStatusServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        int application_id = Integer.parseInt(request.getParameter("application_id"));
        String status = request.getParameter("status");

        ApplicationDAO dao = new ApplicationDAO();

        boolean success = dao.updateStatus(application_id, status);

        response.setContentType("text/plain");

        if (success) {
            response.getWriter().write("success");
        } else {
            response.getWriter().write("error");
        }
    }
}