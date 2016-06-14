<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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

<div id="nav">
<%@ include file="framework/nav.jsp" %>
</div>

<div id="section">
<h1>
Transaction History
</h1>
<Form action = "TransactionHistory.jsp">
<table style="width:100%">
  <tr>
    <th>Sender</th>
    <th>Recipient</th> 
    <th>Amount</th>
    <th>Date</th>
    <th>Time</th>
  </tr>
  <c:forEach var="transaction" items="${transhist}">
        <tr>
          <td>${transaction.sender}</td>
          <td>${transaction.recipient}</td>
          <td>${transaction.amount}</td>
          <td>${transaction.date}</td>
          <td>${transaction.time}</td>
           </c:forEach>
</table>
</Form>
</div>
          
<div id="footer">
Copyright © Michael Romer & Jesper Douglas
</div>

</body>
</html>