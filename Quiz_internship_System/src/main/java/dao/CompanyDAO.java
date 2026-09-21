package dao;

import java.sql.*;
import java.util.*;
import model.Company;

public class CompanyDAO {


    public boolean addCompany(Company c) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "INSERT INTO companies(company_name, location, eligibility_cgpa) VALUES(?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, c.getCompany_name());
            ps.setString(2, c.getLocation());
            ps.setDouble(3, c.getEligibility_cgpa());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // GET ALL
    public List<Company> getAllCompanies() {
        List<Company> list = new ArrayList<>();

        try {
            Connection con = DBConnection.getConnection();

            String sql = "SELECT * FROM companies";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Company c = new Company();
                c.setCompany_id(rs.getInt("company_id"));
                c.setCompany_name(rs.getString("company_name"));
                c.setLocation(rs.getString("location"));
                c.setEligibility_cgpa(rs.getDouble("eligibility_cgpa"));

                list.add(c);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean updateCompany(Company c){
        try{
            Connection con = DBConnection.getConnection();

            String q = "UPDATE companies SET company_name=?, location=?, eligibility_cgpa=? WHERE company_id=?";

            PreparedStatement ps = con.prepareStatement(q);
            ps.setString(1, c.getCompany_name());
            ps.setString(2, c.getLocation());
            ps.setDouble(3, c.getEligibility_cgpa());
            ps.setInt(4, c.getCompany_id());

            ps.executeUpdate();
            return true;

        }catch(Exception e){ e.printStackTrace(); }

        return false;
    }
    // DELETE
    public boolean deleteCompany(int id) {
        try {
            Connection con = DBConnection.getConnection();

            String sql = "DELETE FROM companies WHERE company_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}