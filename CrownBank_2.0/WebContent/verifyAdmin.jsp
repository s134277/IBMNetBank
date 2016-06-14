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

<% 
String name = request.getParameter("name");
String tel= request.getParameter("tel");
String post = request.getParameter("post"); 
String userID =(String)request.getParameter("userID");
String password =(String)request.getParameter("password1");
String passwordConfirm =(String)request.getParameter("password2");
String currency =(String)request.getParameter("currency");
String isAdmin =(String)request.getParameter("isAdmin");
if(isAdmin==null){
%>
<jsp:forward page="CreateAcc"> 
<jsp:param name="name" value="<%=name%>" /> 
<jsp:param name="tel" value="<%=tel%>" /> 
<jsp:param name="post" value="<%=post%>" /> 
<jsp:param name="userID" value="<%=userID%>" /> 
<jsp:param name="password1" value="<%=password%>" />
<jsp:param name="password2" value="<%=passwordConfirm%>" />
<jsp:param name="currency" value="<%=currency%>" />
</jsp:forward> 
<% } %>


<div id="header">
		<img src="pictures/CrownBankLogo2.png" alt="Logo" width="800" height="140">
</div>

	<div id="section">
	<center>
      	<h1>Verify</h1>
      	By entering an administrator ID:
      	<form method="post" action="VerifyAdmin">
        	<p><input type="text" name="loginVerify" value="" placeholder="Username"  style="text-align: center"></p>
       		<p><input type="password" name="passwordVerify" value="" placeholder="Password"  style="text-align: center"></p>
 			<p><input type="hidden" name="name" value="<%= name%>"></p>
        	<p><input type="hidden" name="tel" value="<%= tel%>" ></p>
        	<p><input type="hidden" name="post" value="<%= post%>" ></p>
        	<p><input type="hidden" name="userID" value="<%= userID%>" ></p>
       		<p><input type="hidden" name="password1" value="<%= password%>" ></p>
       		<p><input type="hidden" name="password2" value="<%= passwordConfirm%>" ></p>
       		<p><input type="hidden" name="isAdmin" value="on" ></p>
       		<p><input type="hidden" name="currency" value="<%= currency%>" ></p>
       		      		
        <p class="submit"><input type="submit" name="commit" value="Verify"></p>
      </form>
	<form method="post" action="home.jsp">
		<p class="submit"><input type="submit" name="commit" value="Cancel"></p>
	</form>
	</center>
	<%if(request.getAttribute("VerifyFailed")!=null){ %>
	<center>
		<font color = red> Verification failed </font> 
	</center>
	<% } %>
</div>
	



<div id="footer">
Copyright © Michael Romer and Jesper Douglas
</div>

</body>
</html>