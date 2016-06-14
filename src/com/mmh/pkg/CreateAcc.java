package com.mmh.pkg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

@WebServlet("/CreateAcc")
public class CreateAcc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/exampleDS")
	private	DataSource ds1; 
	
	
	public CreateAcc() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext sc = this.getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/createAcc.jsp");
		int tel = -1;
		int post = -1;
		// Getting parameters from HTTP request and checking for simple validity: 
		// (advanced validity is a model check in the database)
		String name = request.getParameter("name");
		try{
			tel = Integer.parseInt(request.getParameter("tel"));
			request.setAttribute("badTelephoneNumber", "false");
		} catch(Exception e){
			request.setAttribute("badTelephoneNumber", "true");
		}
		
		try{
			post = Integer.parseInt(request.getParameter("post"));
			request.setAttribute("badPostalCode", "false");
		} catch(Exception e){
			request.setAttribute("badPostalCode", "true");
		}
		
		String userID =(String)request.getParameter("userID");
		String password =(String)request.getParameter("password1");
		String passwordConfirm =(String)request.getParameter("password2");
		String isAdmin =(String)request.getParameter("isAdmin");
		String currency =(String)request.getParameter("currency");
		String type;
		if(isAdmin!=null){
			type = "Administrator";
		} else{
			type = "Client";
		}
		
		if(password.equals(passwordConfirm)){
			request.setAttribute("confirmedPassword", "true");
		} else{
			request.setAttribute("confirmedPassword", "false");
		}
		
		userData user = new userData();
		user.setUsername(userID);
		user.setFullName(name);
		user.setCurrency(currency);
		user.setType(type);
		user.setPassword(password);
		user.setPostnumber(post);
		user.setTelephoneNumber(tel);
		
		
		//if validity is alright, connect to DB and request to create user:
		if(tel!=-1 && post!=-1 && request.getAttribute("confirmedPassword")=="true"){
	
			Connection con = null;
			String conUser = "DTU07";
			String conPassword = "FAGP2016";
			try{
				con=ds1.getConnection(conUser,conPassword);

			} catch (SQLException e) {
				e.printStackTrace();
			}
			Controller control = new Controller();
			String result = control.CreateAcc(user,con);
			
			String[] results = result.split(";");
			
					
			 if(Integer.parseInt(results[0])!=0){
			 	request.setAttribute("success", "true");
			 	request.setAttribute("status", null);
			 } else{
			 	request.setAttribute("success", "false");
				request.setAttribute("status", results[1]);
			}
		} else{ //in case telephone/postal code could not be parsed as int or password 1 and 2 dont match.
			String status = "check that the password is the same in both input fields, and the telephone and postal code only contains integers";
			//out.println("Unsuccesfully registered, with error: input is invalid, check that the password is the same in both input fields, and the telephone and postal code only contains integers");
	 		request.setAttribute("success", "false");
	 		request.setAttribute("status", status);
		}
		
		//out.println("Success: " + request.getAttribute("success"));
		//out.println("status: " + request.getParameter("status"));
		
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
