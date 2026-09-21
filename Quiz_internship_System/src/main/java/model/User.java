package model;

public class User {
    private int userId;
    private String role;
    private int studentId;
    private String name;
    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) 
    {
    	this.studentId = studentId; 
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}