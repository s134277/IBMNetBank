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
 * Servlet implementation class EditBankAcc
 */
@WebServlet("/EditBankAccLayout")
public class EditBankAccLayout extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/exampleDS")
	private	DataSource ds1;   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditBankAccLayout() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
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
		ArrayList<String> currencies = new ArrayList<String>();
		try {
			currencies = control.getCurrencies(con);		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		request.setAttribute("currencies", currencies);
		
		ServletContext sc = this.getServletContext();
		
		RequestDispatcher rd = sc.getRequestDispatcher("/editBankAcc.jsp");
		

		rd.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
