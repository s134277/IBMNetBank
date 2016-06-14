package com.mmh.pkg;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
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

@WebServlet("/NewBAcc")
public class NewBAcc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/exampleDS")
	private	DataSource ds1;
	
    public NewBAcc() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String currency = request.getParameter("currency");
		BigDecimal intrest = new BigDecimal("3.00");
		String userID = (String) request.getSession().getAttribute("userID");
		
		PrintWriter out = response.getWriter();
		ServletContext sc = this.getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/newBAcc.jsp");
		
		Controller control = new Controller();
		Connection con = null;
		String conUser = "DTU07";
		String conPassword = "FAGP2016";
		try{
			con=ds1.getConnection(conUser,conPassword);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		String result = control.CreateBankAcc(currency,userID,intrest,con);
		
		String[] results = result.split(";");
		
		
		 if(Integer.parseInt(results[0])!=0){
		 	out.println("Successfully registered");
		 	request.setAttribute("success", "true");
		 	request.setAttribute("accNumber", results[1]);
		 } else{
		 	out.println("Unsuccesfully registered, with error: " + results[1]);
		 	request.setAttribute("success", "false");
			request.setAttribute("accNumber", null);
		}
		 
		 rd.forward(request, response); 
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

