package model;

public class Application {

    private int application_id;
    private int student_id;

    private String status;
    private String internship_role;
    private String company_name;
    private String location;
    private String applied_date;

    public int getApplication_id() { return application_id; }
    public void setApplication_id(int application_id) { this.application_id = application_id; }

    public int getStudent_id() { return student_id; }
    public void setStudent_id(int student_id) { this.student_id = student_id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getInternship_role() { return internship_role; }
    public void setInternship_role(String internship_role) { this.internship_role = internship_role; }

    public String getCompany_name() { return company_name; }
    public void setCompany_name(String company_name) { this.company_name = company_name; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getApplied_date() { return applied_date; }
    public void setApplied_date(String applied_date) { this.applied_date = applied_date; }
    
    private String student_name;
    private double score;

    // getters & setters
    public String getStudent_name() { return student_name; }
    public void setStudent_name(String student_name) { this.student_name = student_name; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    private double cgpa;

    public double getCgpa() { return cgpa; }
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }
}