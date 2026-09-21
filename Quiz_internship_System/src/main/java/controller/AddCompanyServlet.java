package controller;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import dao.CompanyDAO;
import model.Company;

@WebServlet("/addCompany")
public class AddCompanyServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        Company c = new Company();

        c.setCompany_name(req.getParameter("company_name")); 
        c.setLocation(req.getParameter("location"));
        String cgpaStr = req.getParameter("eligibility_cgpa");

        new CompanyDAO().addCompany(c);

        res.sendRedirect("adminCompanies");
    }
}