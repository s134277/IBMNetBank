<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<% 
	String userID = (String)session.getAttribute("userID");
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

	<!--  The section source code is copied from http://www.cssflow.com/snippets/login-form/demo/html  -->
	<div id="section">
      	<h1>Transaction</h1>
      	<form action="Transaction">
        	<select name="item">
    		<c:forEach var="bankaccount" items="${bankaccounts}">
    			<option value= {${bankaccount.accountnumber}:${bankaccount.currency}} >${bankaccount.accountnumber}</option>
  			</c:forEach>	
  			</select>
        	<p><input type="text" name="recipient" value="" placeholder="Recipient" ></p>
       		<p><input type="text" name="amount" value="" placeholder="Amount" required></p>
        <p class="submit"><input type="submit" name="commit" value="Submit"></p>
      </form>
    </div>
<% 
String status = (String)request.getAttribute("status");
if(status!=null) {%>

<font color = red> <%= status%> </font> 

<% } %>
<div id="footer">
Copyright © Michael Romer and Jesper Douglas
</div>

</body>
</html>