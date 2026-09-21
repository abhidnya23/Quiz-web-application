package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Internship;

import java.io.IOException;

import dao.InternshipDAO;

/**
 * Servlet implementation class AddInternshipServlet
 */
@WebServlet("/addInternship")
public class AddInternshipServlet extends HttpServlet
	{

	    protected void doPost(HttpServletRequest req, HttpServletResponse res)
	            throws ServletException, IOException {

	        Internship i = new Internship();

	        i.setCompany_id(Integer.parseInt(req.getParameter("company_id")));
	        i.setRole(req.getParameter("role"));
	        i.setStipend(Double.parseDouble(req.getParameter("stipend")));

	        java.sql.Date deadline = java.sql.Date.valueOf(req.getParameter("deadline"));
	        i.setDeadline(deadline);

	        new InternshipDAO().addInternship(i);

	        res.sendRedirect("adminInternships");
	    }
	}


