<center>

<br>
<% 
boolean isLoggedIn = (boolean)request.getSession().getAttribute("isLoggedIn");
if(isLoggedIn==false) {
 %>


<Form action = "login.jsp">
<input type = "Submit" value = "Login" />
</Form>

<br>

<Form action = "createAcc.jsp">
<input type = "Submit" value = "Create Account" />
</Form>

<br>

<Form action = "home.jsp">
<input type = "Submit" value = "Home" />
</Form>

<%} else{ %>
<Form action = "home.jsp">
<input type = "Submit" value = "Logout" />
</Form>

<br>

<Form action = "editAcc.jsp">
<input type = "Submit" value = "Edit Account" />
</Form>

<br>

<Form action = "newBAcc.jsp">
<input type = "Submit" value = "New Bank Account" />
</Form>

<br>

<Form action = "userHome.jsp">
<input type = "Submit" value = "Home" />
</Form>

<% } %>
<br>

<img src="pictures/Logo.png" alt="Logo" width="140" height="140">
</center>