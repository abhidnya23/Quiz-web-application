<%@ page import="java.util.*, model.Application" %>

<html>
<head>
    <title>Admin Applications</title>

    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">

    <script>
    function updateStatus(appId) {

        let status = document.getElementById("status_" + appId).value;

        fetch("<%=request.getContextPath()%>/updateStatus", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
            body: "application_id=" + appId + "&status=" + status
        })
        .then(response => response.text())
        .then(data => {

            if(data.trim() === "success"){

                alert("Status Updated ✅");

                // ✅ Update UI instantly
                let statusCell = document.getElementById("statusText_" + appId);

                if(status === "SELECTED"){
                    statusCell.innerHTML = "<span style='color:green;'>SELECTED</span>";
                }
                else if(status === "REJECTED"){
                    statusCell.innerHTML = "<span style='color:red;'>REJECTED</span>";
                }
                else if(status === "SHORTLISTED"){
                    statusCell.innerHTML = "<span style='color:orange;'>SHORTLISTED</span>";
                }
                else{
                    statusCell.innerHTML = "APPLIED";
                }

            } else {
                alert("Update Failed ❌");
            }

        })
        .catch(error => {
            alert("Error ❌");
        });
    }
    </script>
</head>

<body>
<div style="float:right;">
    <a href="<%=request.getContextPath()%>/logout"
       style="
        background:#e84118;
        color:white;
        padding:8px 12px;
        text-decoration:none;
        border-radius:5px;
       ">
        Logout
    </a>
</div>
<h1>Dashboard</h1>
<%
String role = (String) session.getAttribute("role");

if (role == null || !role.equals("ADMIN")) {
    response.sendRedirect("login.jsp");
    return;
}
%>

<!-- 🔥 NAVBAR -->
<hr>
<a href="<%=request.getContextPath()%>/adminApplications">Applications</a> |
<a href="<%=request.getContextPath()%>/adminCompanies">Companies</a> |
<a href="<%=request.getContextPath()%>/adminInternships">Internships</a> |
<a href="<%=request.getContextPath()%>/adminReport">Reports</a>

<hr>

<h2>All Applications</h2>

<table border="1" cellpadding="10">
<tr>
	<th>ID</th>
	<th>Student ID</th>
	<th>Name</th>
	<th>CGPA</th>   <!-- NEW -->
	<th>Role</th>
	<th>Company</th>
	<th>Score</th>
	<th>Status</th>
	<th>Update</th>
</tr>

<%
List<Application> list = (List<Application>) request.getAttribute("list");

if (list != null && !list.isEmpty()) {
    for(Application a : list){

        boolean isFinal = "SELECTED".equals(a.getStatus()) || "REJECTED".equals(a.getStatus());
%>

<tr>
    <td><%= a.getApplication_id() %></td>
    <td><%= a.getStudent_id() %></td>

    <!-- 🔥 STUDENT NAME -->
    <td><%= a.getStudent_name() %></td>
	<td>
    <% if(a.getCgpa() == 0){ %>
        —
    <% } else { %>
        <%= a.getCgpa() %>
    <% } %>
</td>
    <td><%= a.getInternship_role() %></td>
    <td><%= a.getCompany_name() %></td>

  <td>
    <% if(a.getScore() == 0){ %>
        <span style="color:gray;">Not Attempted</span>
    <% } else if("SHORTLISTED".equals(a.getStatus()) || "SELECTED".equals(a.getStatus())){ %>
        <b><%= a.getScore() %></b>
    <% } else { %>
        <span style="color:gray;">pending</span>
    <% } %>
</td>

    <!-- 🔥 STATUS -->
    <td id="statusText_<%=a.getApplication_id()%>">
    <%
        String status = a.getStatus();

        if("SELECTED".equals(status)){
    %>
        <span style="color:green;">SELECTED</span>
    <%
        } else if("REJECTED".equals(status)){
    %>
        <span style="color:red;">REJECTED</span>
    <%
        } else if("SHORTLISTED".equals(status)){
    %>
        <span style="color:orange;">SHORTLISTED</span>
    <%
        } else {
    %>
        APPLIED
    <%
        }
    %>
    </td>

    <!-- 🔥 UPDATE -->
    <td>
        <select id="status_<%= a.getApplication_id() %>" <%= isFinal ? "disabled" : "" %>>

            <option value="APPLIED" <%= "APPLIED".equals(a.getStatus()) ? "selected" : "" %>>APPLIED</option>

            <option value="SHORTLISTED" <%= "SHORTLISTED".equals(a.getStatus()) ? "selected" : "" %>>SHORTLISTED</option>

            <option value="SELECTED" <%= "SELECTED".equals(a.getStatus()) ? "selected" : "" %>>SELECTED</option>

            <option value="REJECTED" <%= "REJECTED".equals(a.getStatus()) ? "selected" : "" %>>REJECTED</option>

        </select>

        <button onclick="updateStatus(<%= a.getApplication_id() %>)"
                <%= isFinal ? "disabled" : "" %>>
            Update
        </button>
    </td>
</tr>

<%
    }
} else {
%>

<tr>
    <td colspan="8">No applications found</td>
</tr>

<%
}
%>

</table>

</body>
</html>