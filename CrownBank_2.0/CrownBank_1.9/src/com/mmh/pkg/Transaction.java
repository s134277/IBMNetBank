package com.mmh.pkg;

import java.io.IOException;
import java.math.BigDecimal;
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
 * Servlet implementation class Transaction
 */
@WebServlet("/Transaction")
public class Transaction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/exampleDS")
	private DataSource ds1;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Transaction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		String conUser = "DTU07";
		String conPassword = "FAGP2016";
		try {
			con = ds1.getConnection(conUser, conPassword);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		String username = (String) request.getSession().getAttribute("userID");

		Controller control = new Controller();
		ArrayList<BankAccount> result = null;
		String transaction = null;

		// Data from drop down list
		if (request.getParameter("item") != null) {
			String[] parts = request.getParameter("item").split(":");
			int accnum = Integer.parseInt(parts[0].substring(1, parts[0].length()));
			String currency = parts[1].substring(0, parts[1].length() - 1);
			int recipient = Integer.parseInt(request.getParameter("recipient"));
			BigDecimal amount = new BigDecimal(request.getParameter("amount"));
			//Doing the transfer from inserted data
			try {
				transaction = control.transaction(amount, accnum, recipient, currency, con);
				System.out.println(transaction);
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//sends the status string from transaction to the servlet
		if (transaction != null){
			request.setAttribute("status", transaction);
			request.setAttribute("success", "true");
		}
		else{
			request.setAttribute("success", "false");
			request.setAttribute("status", transaction);
		}

		try {
			result = control.getBankAcc(username, con);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};

		request.setAttribute("bankaccounts", result);
		ServletContext sc = this.getServletContext();

		RequestDispatcher rd = sc.getRequestDispatcher("/transaction.jsp");

		if (request.getAttribute("commit") != null) {
			System.out.println("hallo");
		}

		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
