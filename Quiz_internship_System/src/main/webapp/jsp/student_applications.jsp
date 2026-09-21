<%@ page import="java.util.*, model.Application" %>

<html>
<head>
    <title>My Applications</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>

<body>

<h2>My Applications</h2>

<%
List<Application> list = (List<Application>) request.getAttribute("list");

if(list == null || list.isEmpty()){
%>
    <p>No applications found.</p>
<%
} else {
%>

<table border="1">
<tr>
    <th>ID</th>
    <th>Role</th>
    <th>Company</th>
    <th>Location</th>
    <th>Status</th>
    <th>Applied Date</th>
    <th>Action</th> <!-- 🔥 NEW COLUMN -->
</tr>

<%
for(Application a : list){
%>

<tr>
    <td><%= a.getApplication_id() %></td>
    <td><%= a.getInternship_role() %></td>
    <td><%= a.getCompany_name() %></td>
    <td><%= a.getLocation() %></td>

    <!-- STATUS -->
    <td>
        <%
            String status = a.getStatus();

            if("SELECTED".equals(status)){
        %>
            <span class="status-selected"><%= status %></span>
        <%
            } else if("REJECTED".equals(status)){
        %>
            <span class="status-rejected"><%= status %></span>
        <%
            } else if("SHORTLISTED".equals(status)){
        %>
            <span class="status-shortlisted"><%= status %></span>
        <%
            } else {
        %>
            <span><%= status %></span>
        <%
            }
        %>
    </td>

    <td><%= a.getApplied_date() %></td>

    <!-- 🔥 ACTION COLUMN -->
    <td>
        <%
            if("SHORTLISTED".equals(status)){
        %>
            <!-- ✅ ENABLE BUTTON -->
            <form action="<%=request.getContextPath()%>/startExam" method="get">
   				 <input type="hidden" name="application_id" value="<%= a.getApplication_id() %>">
   				 <input type="submit" value="Give Exam">
			</form>

        <%
            } else {
        %>
            <!-- ❌ DISABLED -->
            <button disabled>Not Allowed</button>
        <%
            }
        %>
    </td>

</tr>

<% } %>

</table>

<% } %>

</body>
</html>