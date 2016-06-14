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

	<div id="section">
		<h1>Bank Accounts</h1>
		<table style="width: 100%" align="left">
			<tr>
				<th>Account Number</th>
				<th>Balance</th>
				<th>Intrest</th>
				<th>Currency</th>
			</tr>
			<c:forEach var="bankaccount" items="${bankaccounts}">
				<tr>
					<td align="center">${bankaccount.accountnumber}</td>
					<td align="center">${bankaccount.balance}</td>
					<td align="center">${bankaccount.intrest}</td>
					<td align="center">${bankaccount.currency}</td>
					<td>
						<form action="TransactionHistory">
							<input type="hidden" name="accnumber"
								value=${bankaccount.accountnumber } /> 
							<input type="hidden"
								name="currency" value=${bankaccount.currency } /> 
								<input
								type="Submit" value="History" />
						</form>
					</td>
					<td>
						<form action = "editBankAcc.jsp">
						<input type="hidden" name="accnumber"
						value =${bankaccount.accountnumber} />
         				<input type="hidden" name="intrest"
								value=${bankaccount.intrest} /> 
							<input type="hidden"
								name="currency" value=${bankaccount.currency} />
         				<input type = "Submit" value = "Edit" />
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
		</form>
	</div>

	<div id="footer">Copyright � Michael Romer & Jesper Douglas</div>

</body>
</html>