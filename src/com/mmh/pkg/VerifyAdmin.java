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

@WebServlet("/VerifyAdmin")
public class VerifyAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/exampleDS")
	private	DataSource ds1; 
	
    public VerifyAdmin() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ServletContext sc = this.getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/CreateAcc");
		
		Connection con = null;
		String conUser = "DTU07";
		String conPassword = "FAGP2016";
		try{
			con=ds1.getConnection(conUser,conPassword);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String userID =(String)request.getParameter("loginVerify");
		String password =(String)request.getParameter("passwordVerify");
		
		Controller control = new Controller();
		try {
			if(control.AdminCheck(userID, password, con).equals("false")){
				request.setAttribute("VerifyFailed", "true");
				rd = sc.getRequestDispatcher("/verifyAdmin.jsp");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		rd.forward(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
