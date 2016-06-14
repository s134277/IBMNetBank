<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%
String userID = (String)request.getSession().getAttribute("userID");
String isAdmin = (String)request.getSession().getAttribute("isAdmin");
 %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WEBBANK</title>
<link rel="stylesheet" type="text/css" href="styles/myStyle.css">
</head>
<body>
<div id="header">
<img src="pictures/CrownBankLogo2.png" alt="Logo" width="800" height="140">
</div>

<%if(isAdmin.equals("true")){ %>
<div id="nav">
<%@include file="framework/navAdmin.jsp" %>
</div>
<% } else{ %>
<div id="nav">
<%@include file="framework/nav.jsp" %>
</div>
<% } %>

<div id="section">
<h1>
You are logged in!
</h1>

Welcome <%= userID%>

</div>

<div id="footer">
Copyright © Michael Romer & Jesper Douglas
</div>
</body>
</html>