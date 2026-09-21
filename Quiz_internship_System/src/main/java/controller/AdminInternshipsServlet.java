package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.CompanyDAO;
import dao.InternshipDAO;

/**
 * Servlet implementation class AdminInternshipsServlet
 */
@WebServlet("/adminInternships")
public class AdminInternshipsServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        InternshipDAO dao = new InternshipDAO();
        CompanyDAO cdao = new CompanyDAO();

        req.setAttribute("internships", dao.getAllInternships());
        req.setAttribute("companies", cdao.getAllCompanies());

        req.getRequestDispatcher("jsp/admin_internships.jsp")
           .forward(req, res);
    }
}

