package model;

public class Company {

    private int company_id;
    private String company_name;
    private String location;
    private double eligibility_cgpa;

    public int getCompany_id() { return company_id; }
    public void setCompany_id(int company_id) { this.company_id = company_id; }

    public String getCompany_name() { return company_name; }
    public void setCompany_name(String company_name) { this.company_name = company_name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public double getEligibility_cgpa() { return eligibility_cgpa; }
    public void setEligibility_cgpa(double eligibility_cgpa) { this.eligibility_cgpa = eligibility_cgpa; }
}