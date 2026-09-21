<%@ page language="java" %>
<%
String msg = (String) session.getAttribute("success_msg");

if(msg != null){
%>

<div style="
    background:#d4edda;
    color:#155724;
    padding:12px;
    margin:15px;
    border-radius:5px;
    font-weight:bold;
">
    <%=msg%>
</div>

<%
    session.removeAttribute("success_msg"); // remove after showing
}
%>
<%

String role = (String) session.getAttribute("role");

if (role == null || !role.equals("STUDENT")) {
    response.sendRedirect("login.jsp");
    return;
}

String name = (String) session.getAttribute("name");
%>

<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>

<body>

<h2>Welcome <%= name %></h2>

<hr>

<ul>
    <li><a href="<%=request.getContextPath()%>/internships">View Internships</a></li>

    <li><a href="<%=request.getContextPath()%>/myApplications">My Applications</a></li>

    <!-- 🔥 NEW FEATURE -->
    <li><a href="<%=request.getContextPath()%>/studentResults">RESULT</a></li>

</ul>

<br><br>

<a href="<%=request.getContextPath()%>/logout">Logout</a>

</body>
</html>