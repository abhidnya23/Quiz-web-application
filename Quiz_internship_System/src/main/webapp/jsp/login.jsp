<%@ page language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/style.css">
</head>

<body class="bg-mesh">

<div class="login-card" style="width:350px; margin:auto; margin-top:100px; padding:30px;">

<h2 style="text-align:center;">Login</h2>

<%
if (request.getParameter("error") != null) 
{
%>
<p style="color:red; text-align:center;">Invalid credentials</p>
<% } %>

<form action="<%=request.getContextPath()%>/login" method="post">

    <input class="input-field" type="text" name="email" placeholder="Email" style="width:100%; padding:10px;"><br><br>

    <input class="input-field" type="password" name="password" placeholder="Password" style="width:100%; padding:10px;"><br><br>

    <input class="btn-primary" type="submit" value="Login" style="width:100%; padding:10px;">

</form>

</div>

</body>
</html>