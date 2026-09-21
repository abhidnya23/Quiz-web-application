package controller;

import dao.userdao;
import model.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/login")
public class login extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();

        userdao dao = new userdao();
        User user = dao.getUser(email, password);

        if (user != null) {

            HttpSession session = request.getSession();

            session.setAttribute("user_id", user.getUserId());   // Integer
            session.setAttribute("role", user.getRole());
            session.setAttribute("name", user.getName());
            session.setMaxInactiveInterval(15 * 60);

            if ("STUDENT".equals(user.getRole())) {
                session.setAttribute("student_id", user.getStudentId());
                response.sendRedirect(request.getContextPath() + "/jsp/dashboard.jsp");

            } else if ("ADMIN".equals(user.getRole())) {
                response.sendRedirect(request.getContextPath() + "/adminApplications"); 
            }

        } else {
            response.sendRedirect(request.getContextPath() + "/jsp/login.jsp?error=1");
        }
    }
}