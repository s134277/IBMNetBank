<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mmh.pkg.BankAccount"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>WEBBANK</title>
<link rel="stylesheet" type="text/css" href="styles/myStyle.css">
</head>
<body>

	<div id="header">
		<img src="pictures/CrownBankLogo2.png" alt="Logo" width="800"
			height="140">
	</div>

	<div id="nav">
		<%@ include file="framework/nav.jsp"%>
	</div>
<% int accnum = Integer.parseInt(request.getParameter("accnumber")); %>
	<div id="section">
		<h1>
			Edit Bank Account:
			<%=accnum%></h1>
		<form action="EditBankAcc">
			<table style="width: 100%" align="left">
				<tr>
					<th></th>
					<th>Intrest</th>
					<th>Currency</th>
				</tr>
				<td>Current value:</td>
				<td align="center"><%=request.getParameter("intrest")%></td>
				<td align="center"><%=request.getParameter("currency")%></td>
				<tr>
					
				<td>New value:</td>
					<td>
					<p><input type="text" name="intrest" value="" placeholder="Intrest"></p></td>
					<td>
					<select name="item"> 
					<c:forEach var="currency" items="${currencies}">
    			<option value= ${currency} >${currency}</option>
  				
  				</c:forEach>
  				</select>	
					</td>
					<td>
					<input type="Submit" value="Submit changes" />
					</td>
				</tr>
			</table>
		</form>
		<p><input type="hidden" name="accnum" value=<%= accnum%>> ></p></td>
	<% String success = request.getParameter("success");	
	if(success!=null) {%>

<font color = blue> Your account has succesfully been edited </font> 

<% } %>
					

	</div>

	<div id="footer">Copyright © Michael Romer & Jesper Douglas</div>

</body>
</html>