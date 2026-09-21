package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Company;

import java.io.IOException;

import dao.CompanyDAO;

/**
 * Servlet implementation class UpdateCompanyServlet
 */
@WebServlet("/updateCompany")
public class UpdateCompanyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {

        Company c = new Company();

        c.setCompany_id(Integer.parseInt(req.getParameter("company_id")));
        c.setCompany_name(req.getParameter("company_name"));
        c.setLocation(req.getParameter("location"));
        c.setEligibility_cgpa(Double.parseDouble(req.getParameter("eligibility_cgpa")));

        new CompanyDAO().updateCompany(c);

        res.sendRedirect("adminCompanies");
    }
}


