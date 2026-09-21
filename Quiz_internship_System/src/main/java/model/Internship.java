package model;

import java.sql.Date;

public class Internship {

    private int internship_id;
    private int company_id;   // ✅ IMPORTANT

    private String role;
    private double stipend;
    private Date deadline;

    // joined fields
    private String company_name;
    private String location;
    private double eligibility_cgpa;

    // ===== GETTERS & SETTERS =====

    public int getInternship_id() {
        return internship_id;
    }

    public void setInternship_id(int internship_id) {
        this.internship_id = internship_id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getStipend() {
        return stipend;
    }

    public void setStipend(double stipend) {
        this.stipend = stipend;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getEligibility_cgpa() {
        return eligibility_cgpa;
    }

    public void setEligibility_cgpa(double eligibility_cgpa) {
        this.eligibility_cgpa = eligibility_cgpa;
    }
}