package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

import dao.DBConnection;

/**
 * Servlet implementation class UpdateResultServlet
 */
@WebServlet("/updateResult")
public class UpdateResultServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int application_id = Integer.parseInt(request.getParameter("application_id"));
        String action = request.getParameter("action");

        try(Connection con = DBConnection.getConnection()){

            PreparedStatement ps = con.prepareStatement(
                "UPDATE applications SET status=? WHERE application_id=?"
            );

            ps.setString(1, action);
            ps.setInt(2, application_id);

            ps.executeUpdate();

            response.getWriter().write("success");

        } catch(Exception e){
            e.printStackTrace();
            response.getWriter().write("fail");
        }
    }
}
