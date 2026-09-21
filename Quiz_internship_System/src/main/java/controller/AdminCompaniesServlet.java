package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import dao.CompanyDAO;

/**
 * Servlet implementation class AdminCompaniesServlet
 */
@WebServlet("/adminCompanies")
public class AdminCompaniesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        CompanyDAO dao = new CompanyDAO();
        req.setAttribute("companies", dao.getAllCompanies());

        req.getRequestDispatcher("jsp/admin_companies.jsp")
           .forward(req, res);
    }
}


