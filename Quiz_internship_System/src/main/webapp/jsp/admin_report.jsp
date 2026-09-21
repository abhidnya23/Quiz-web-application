<%@ page import="java.util.*" %>

<html>
<head>
<title>Admin Reports</title>
</head>

<body>

<h2>Admin Reports Dashboard</h2>

<hr>

<a href="<%=request.getContextPath()%>/adminApplications">Applications</a> |
<a href="<%=request.getContextPath()%>/adminCompanies">Companies</a> |
<a href="<%=request.getContextPath()%>/adminInternships">Internships</a> |
<a href="<%=request.getContextPath()%>/adminReport">Reports</a> |
<a href="<%=request.getContextPath()%>/logout">Logout</a>

<hr>

<!-- ================= 1 ================= -->
<h3>Students Selected per Company</h3>

<table border="1">
<tr><th>Company</th><th>Count</th></tr>

<%
List<Map<String,Object>> companyStats = (List<Map<String,Object>>)request.getAttribute("companyStats");
for(Map row : companyStats){
%>
<tr>
<td><%=row.get("company")%></td>
<td><%=row.get("count")%></td>
</tr>
<% } %>
</table>

<hr>

<!-- ================= 2 ================= -->
<h3>Applications per Internship</h3>

<table border="1">
<tr><th>Role</th><th>Company</th><th>Count</th></tr>

<%
List<Map<String,Object>> appStats = (List<Map<String,Object>>)request.getAttribute("applicationStats");
for(Map row : appStats){
%>
<tr>
<td><%=row.get("role")%></td>
<td><%=row.get("company")%></td>
<td><%=row.get("count")%></td>
</tr>
<% } %>
</table>

<hr>

<!-- ================= 3 ================= -->
<h3>Exam Rank List</h3>

<table border="1">
<tr><th>Rank</th><th>User</th><th>Marks</th></tr>

<%
List<Map<String,Object>> rankList = (List<Map<String,Object>>)request.getAttribute("rankList");
int r = 1;
for(Map row : rankList){
%>
<tr>
<td><%=r++%></td>
<td><%=row.get("user_id")%></td>
<td><%=row.get("marks")%></td>
</tr>
<% } %>
</table>

<hr>

<!-- ================= 4 ================= -->
<h3>Suspicious Activity Logs</h3>

<table border="1">
<tr><th>Time</th><th>User</th><th>Action</th></tr>

<%
List<Map<String,Object>> logs = (List<Map<String,Object>>)request.getAttribute("logs");
for(Map row : logs){
%>
<tr>
<td><%=row.get("time")%></td>
<td><%=row.get("user_id")%></td>
<td><%=row.get("action")%></td>
</tr>
<% } %>
</table>

</body>
</html>