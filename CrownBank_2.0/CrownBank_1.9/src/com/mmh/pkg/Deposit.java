package com.mmh.pkg;

import java.io.IOException;
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

/**
 * Servlet implementation class Deposit
 */
@WebServlet("/Deposit")
public class Deposit extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/exampleDS")
	private DataSource ds1;
   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deposit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Initialize the connection
				Connection con = null;
				String conUser = "DTU07";
				String conPassword = "FAGP2016";
				try {
					con = ds1.getConnection(conUser, conPassword);

				} catch (SQLException e) {
					e.printStackTrace();
				}

				Controller control = new Controller();

				//Fetching data from textboxes.
				String status = null;
				int accnumb = Integer.parseInt(request.getParameter("accnumber"));
				BigDecimal amount = new BigDecimal(request.getParameter("amount"));
				String currency = request.getParameter("currencyItems");
				try {
					status = control.deposit(accnumb, amount, currency, con);
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				//
				if (status.equals("1")) {
					request.setAttribute("status", "Money succesfully deposited");
					request.setAttribute("success", "true");
				} else {
					request.setAttribute("status", "Bank account doesn't exist");
					request.setAttribute("success", "false");
				}
				
				ServletContext sc = this.getServletContext();

				RequestDispatcher rd = sc.getRequestDispatcher("DepositLayout");

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
