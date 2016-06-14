<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<% 
	String userID = (String)session.getAttribute("userID");
	System.out.println(userID); 

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

	<div id="nav">
		<%@include file="framework/nav.jsp" %>
	</div>

	<div id="section">
      	<h1>Edit your account</h1>
      	
 <%if(request.getAttribute("success")!=null){
	if(request.getAttribute("success")=="false"){ %>

<font color = red> Something went wrong while processing you request.. <br> </font> 
<% } else{ %>
<font color = blue> Your changes has succesfully saved! <br> </font> 
<% } }%>

<%if(request.getAttribute("success")==null)  %>
    <br>  	
      	Please enter new data in the fields you want to change:
      	<form method="post" action="EditAcc">
        	<p><input type="text" name="tel" value="" placeholder="Telephone number"></p>
        	<p><input type="text" name="post" value="" placeholder="Postal number"></p>
       		<p><input type="password" name="password1" value="" placeholder="New password"></p>
       		<p><input type="password" name="password2" value="" placeholder="Repeat new password*"></p>
       		Currency:
       		<select name="currency">
    			<option value="USD">USD</option>
    			<option value="DKK">DKK</option>
    			<option value="GBP">GBP</option>
  			</select>
 
        <p class="submit"><input type="submit" name="commit" value="Save changes"></p>
      </form>
	
	
	<form method="post" action="DeleteAcc">
        <p class="submit"><input type="submit" name="commit" value="Delete Account"></p>
      </form>
      
    </div>

<div id="footer">
Copyright © Michael Romer and Jesper Douglas
</div>

</body>
</html>