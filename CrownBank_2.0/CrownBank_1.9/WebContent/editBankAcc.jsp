<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*"%>
<%@ page import="com.mmh.pkg.Controller"%>
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
	<%
		int accnum = Integer.parseInt(request.getParameter("accnumber"));
		String currency = (String) request.getParameter("currency");
		String intrest = (String) request.getParameter("intrest");
		String accname = (String) request.getParameter("accname");
	%>
	<div id="section">
		<h1>
			Edit Bank Account:
			<%=accname%> : <%=accnum%></h1>
		<form action="EditBankAcc">
			<table style="width: 100%" align="left">
				<tr>
					<th></th>
					<th>Name</th>
					<th>Intrest</th>
					<th>Currency</th>
				</tr>
				<tr>
					<td>Current value:</td>
					<td></td>
					<td align="center"><%=intrest%></td>
					<td align="center"><%=currency%></td>
				</tr>
				<tr>
					<td>New value:</td>
					<td><input type="text" name="accname" value="<%=accname%>" placeholder="Account name"></td>
						
					<td>
						<%
							if (request.getSession().getAttribute("isAdmin").equals("true")) {
						%>
						<p>
							<input type="text" name="intrest" value="<%=intrest%>" placeholder="Intrest">
						</p> <%
 	}
 %>
					</td>
					<td><select name="currencyItems">
							<c:forEach var="currency" items="${currencies}">
								<option value=${currency} >${currency}</option>

							</c:forEach>
					</select></td>
					<td><input type="Submit" value="Submit Changes" /></td>
					<td>
						<p>
							<input type="hidden" name="accnumber" value="<%=accnum%>">
						</p>
						<p>
							<input type="hidden" name="currentIntrest"
								value="<%=intrest%>">
						</p>
					</td>
				</tr>
			</table>
		</form>
						<form action="DeleteBankAcc">
							<input type="Submit" value="Delete Account" /> 
							<input type="hidden" name="accnum" value="<%=accnum%>" />
						</form>
			

		<%
			String success = request.getParameter("success");
			if (success != null) {
		%>

		<font color=blue> Your account has succesfully been edited </font>

		<%
			}
		%>


	</div>

	<div id="footer">Copyright © Michael Romer & Jesper Douglas</div>

</body>
</html>