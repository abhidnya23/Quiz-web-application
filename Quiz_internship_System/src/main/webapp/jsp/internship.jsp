<%@ page import="java.util.*, model.Internship" %>
<head>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>
<h2>Available Internships</h2>

<%
List<Internship> list = (List<Internship>) request.getAttribute("list");
Double cgpaObj = (Double) request.getAttribute("student_cgpa");
List<Integer> appliedList = (List<Integer>) request.getAttribute("appliedList");


double student_cgpa = (cgpaObj != null) ? cgpaObj : 0.0;

if(list == null) list = new ArrayList<>();
if(appliedList == null) appliedList = new ArrayList<>();
%>

<table border="1">
<tr>
    <th>Role</th>
    <th>Company</th>
    <th>Location</th>
    <th>Stipend</th>
    <th>Deadline</th>
    <th>Eligibility</th>
    <th>Action</th>
</tr>

<%
if(list.isEmpty()){
%>
<tr>
    <td colspan="8">No internships available</td>
</tr>
<%
} else {
for(Internship i : list){
%>

<tr>
    <td><%= i.getRole() %></td>
    <td><%= i.getCompany_name() %></td>
    <td><%= i.getLocation() %></td>
    <td><%= i.getStipend() %></td>
    <td><%= i.getDeadline() %></td>

    <td>
        Required: <%= i.getEligibility_cgpa() %><br>
        Yours: <%= student_cgpa %>
    </td>

 <td>
<%
    boolean alreadyApplied = appliedList.contains(i.getInternship_id());
    java.sql.Date today = new java.sql.Date(System.currentTimeMillis());

    if(i.getDeadline() != null && i.getDeadline().before(today)){
%>
        <button disabled style="color:gray;">Deadline Passed</button>
<%
    }
    else if(alreadyApplied){
%>
        <button disabled style="color:blue;">Already Applied ✔</button>
<%
    }
    else if(student_cgpa < i.getEligibility_cgpa()){
%>
        <button disabled style="color:red;">Not Eligible ❌</button>
<%
    }
    else{
%>
        <form action="<%=request.getContextPath()%>/apply" method="post">
            <input type="hidden" name="internship_id" value="<%= i.getInternship_id() %>">
            <input type="submit" value="Apply">
        </form>
<%
    }
%>
</td>
</tr>

<%
}
}
%>

</table>