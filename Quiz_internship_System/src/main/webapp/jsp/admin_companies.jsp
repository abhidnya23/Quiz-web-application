<%@ page import="java.util.*,model.Company" %>

<h2>Manage Companies</h2>

<!-- ADD FORM -->
<form action="addCompany" method="post">
    Name: <input type="text" name="company_name">
    Location: <input type="text" name="location">
    CGPA: <input type="text" name="eligibility_cgpa">
    <input type="submit" value="Add">
</form>

<hr>

<table border="1">
<tr>
    <th>ID</th>
    <th>Name</th>
    <th>Location</th>
    <th>CGPA</th>
    <th>Action</th>
</tr>

<%
List<Company> list = (List<Company>) request.getAttribute("companies");

for(Company c : list){
%>

<tr>
<form action="updateCompany" method="post">
    <td><%= c.getCompany_id() %></td>

    <td>
        <input type="text" name="company_name" value="<%= c.getCompany_name() %>">
    </td>

    <td>
        <input type="text" name="location" value="<%= c.getLocation() %>">
    </td>

    <td>
        <input type="text" name="eligibility_cgpa" value="<%= c.getEligibility_cgpa() %>">
    </td>

    <td>
        <input type="hidden" name="company_id" value="<%= c.getCompany_id() %>">
        <input type="submit" value="Update">
</form>

<form action="deleteCompany" method="post" style="display:inline;">
    <input type="hidden" name="company_id" value="<%= c.getCompany_id() %>">
    <input type="submit" value="Delete">
</form>
    </td>
</tr>

<% } %>

</table>