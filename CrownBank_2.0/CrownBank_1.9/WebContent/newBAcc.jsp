<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
		<%@include file="framework/nav.jsp" %>
	</div>

	<div id="section">
      	<h1>Create new bank account</h1>
      	
 <%if(request.getAttribute("success")!=null){
	if(request.getAttribute("success")=="false"){ %>

<font color = red> Something went wrong while processing you request.. <br> 
Error: Unable to generate a bank account
</font> 
<% } else{ %>
<font color = blue> Your bank account has succesfully been created! <br> 
With account number: <%= request.getAttribute("accNumber") %>
</font> 
<% } }%>

<%if(request.getAttribute("success")==null){  %>
    <br>  	
      	Please select the currency you want the bank account in:
      	<form method="post" action="NewBAcc">
      		<input type=hidden id="thisField" name="inputName">
       		<select name="currency">
    			<option value="USD">USD</option>
    			<option value="DKK">DKK</option>
    			<option value="GBP">GBP</option>
  			</select>
        <p class="submit"><input type="submit" name="commit" value="Create Account"></p>
      </form>

      	(Your account will be assigned an account number automatically)
      <% } %>
      
    </div>

<div id="footer">
Copyright © Michael Romer and Jesper Douglas
</div>

</body>
</html>