package controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import dao.DBConnection;

@WebServlet("/generateCertificate")
public class GenerateCertificateServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        int application_id = Integer.parseInt(request.getParameter("application_id"));

        try (Connection con = DBConnection.getConnection()) {

            String query = "SELECT u.name, c.company_name, i.role " +
                    "FROM applications a " +
                    "JOIN users u ON a.student_id = u.user_id " +
                    "JOIN internships i ON a.internship_id = i.internship_id " +
                    "JOIN companies c ON i.company_id = c.company_id " +
                    "WHERE a.application_id=? AND a.status='SELECTED'";

            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, application_id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                String name = rs.getString("name");
                String company = rs.getString("company_name");
                String role = rs.getString("role");

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=certificate.pdf");

                Document document = new Document(PageSize.A4);
                PdfWriter.getInstance(document, response.getOutputStream());

                document.open();

                // 🎨 Fonts
                Font titleFont = new Font(Font.FontFamily.HELVETICA, 26, Font.BOLD);
                Font normalFont = new Font(Font.FontFamily.HELVETICA, 14);
                Font boldFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);

                // 🏆 Title
                Paragraph title = new Paragraph("CERTIFICATE OF INTERNSHIP", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                document.add(title);

                document.add(new Paragraph("\n\n"));

                // 📄 Body
                Paragraph content = new Paragraph();
                content.setAlignment(Element.ALIGN_CENTER);

                content.add(new Chunk("This is to certify that\n\n", normalFont));
                content.add(new Chunk(name + "\n\n", boldFont));
                content.add(new Chunk("has successfully completed an internship at\n\n", normalFont));
                content.add(new Chunk(company + "\n\n", boldFont));
                content.add(new Chunk("for the role of\n\n", normalFont));
                content.add(new Chunk(role + "\n\n", boldFont));

                document.add(content);

                document.add(new Paragraph("\n\n\n"));

                // ✍ Signature
                Paragraph sign = new Paragraph("Authorized Signature", normalFont);
                sign.setAlignment(Element.ALIGN_RIGHT);
                document.add(sign);

                document.close();

            } else {
                response.getWriter().println("Not authorized");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}