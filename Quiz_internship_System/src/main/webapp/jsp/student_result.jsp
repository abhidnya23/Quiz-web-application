<%@ page import="java.util.*, model.Application" %>

<html>
<head>
<title>My Results</title>
</head>

<body>

<h2>My Applications</h2>

<table border="1" cellpadding="10">
<tr>
    <th>Company</th>
    <th>Role</th>
    <th>Status</th>
    <th>Score</th>
    <th>Certificate</th>
</tr>

<%
List<Application> list = (List<Application>) request.getAttribute("list");

if(list != null){
    for(Application a : list){
%>

<tr>
    <td><%= a.getCompany_name() %></td>
    <td><%= a.getInternship_role() %></td>

    <td>
        <% if("SELECTED".equals(a.getStatus())){ %>
            <span style="color:green;">SELECTED</span>
        <% } else if("REJECTED".equals(a.getStatus())){ %>
            <span style="color:red;">REJECTED</span>
        <% } else { %>
            <%= a.getStatus() %>
        <% } %>
    </td>

  <td>
    <% if("SELECTED".equals(a.getStatus()) || "SHORTLISTED".equals(a.getStatus())){ %>
        <%= a.getScore() %>
    <% } else { %>
        <span style="color:gray;">pending</span>
    <% } %>
</td>

    <!-- 🔥 CERTIFICATE -->
    <td>
        <% if("SELECTED".equals(a.getStatus())){ %>
            <a href="<%=request.getContextPath()%>/generateCertificate?application_id=<%=a.getApplication_id()%>">
                Download
            </a>
        <% } else { %>
            Not Available
        <% } %>
    </td>

</tr>

<%
    }
}
%>

</table>

</body>
</html>