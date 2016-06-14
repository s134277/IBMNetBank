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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;


@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/exampleDS")
	private	DataSource ds1;
    private HttpSession session; 
 
	 
    public Login() {
        super();
    }
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
		String conUser = "DTU07";
		String conPassword = "FAGP2016";
		try{
			con=ds1.getConnection(conUser,conPassword);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
//		PrintWriter out = response.getWriter();
		String login = request.getParameter("login");
		String password = request.getParameter("password");
	
		Controller control = new Controller();
		int result = control.Login(login,password,con);
		
		ServletContext sc = this.getServletContext();
		request.setAttribute("user", login);
		RequestDispatcher rd = sc.getRequestDispatcher("/userHome.jsp");
		session = request.getSession();
		session.setAttribute("userID", login);
		session.setAttribute("isLoggedIn", true);
		String isAdmin = "false";
		if(result == 1){
			try {
				isAdmin = control.AdminCheck(login, password, con);
			} catch (SQLException e) {
				System.out.println("SQL exception thrown when trying to determine if the user is admin");
				e.printStackTrace();
			}
			request.setAttribute("failedLogin", "false");
		 } else{
		 	request.setAttribute("failedLogin", "true");
		 	session.setAttribute("userID", null);
		 	session.setAttribute("isLoggedIn", false);
		 	
		 	rd = sc.getRequestDispatcher("/login.jsp");
		 }
		
		
		session.setAttribute("isAdmin", isAdmin);
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
