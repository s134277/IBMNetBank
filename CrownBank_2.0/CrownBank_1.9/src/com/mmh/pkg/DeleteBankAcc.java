package com.mmh.pkg;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class DeleteBankAcc
 */
@WebServlet("/DeleteBankAcc")
public class DeleteBankAcc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/exampleDS")
	private	DataSource ds1;   
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteBankAcc() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		String conUser = "DTU07";
		String conPassword = "FAGP2016";
		try{
			con=ds1.getConnection(conUser,conPassword);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		Controller control = new Controller();
		int success = 0;
		int accNumber = Integer.parseInt(request.getParameter("accnum"));
		try {
			success = control.deleteBankAcc(accNumber, con);		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		
		if (success == 1){
			request.setAttribute("success", "true");
		} else{
			request.setAttribute("success", "false");
		}
		
		ServletContext sc = this.getServletContext();
		
		RequestDispatcher rd = sc.getRequestDispatcher("ViewBankAcc");
		

		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
