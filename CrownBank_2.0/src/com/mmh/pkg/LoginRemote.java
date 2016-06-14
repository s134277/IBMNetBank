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

@WebServlet("/LoginRemote")
public class LoginRemote extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/exampleDS")
	private	DataSource ds1;
    private HttpSession session; 
    
    public LoginRemote() {
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
		
		ServletContext sc = this.getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/userHome.jsp");
		
		String userAdmin = request.getParameter("remoteAdmin");
		String userTarget = request.getParameter("login");
	
		Controller control = new Controller();
		try {
			userData user = control.getUserInfo(userTarget, con);
			if(user != null){
				request.setAttribute("user", userTarget);
				request.setAttribute("failedLogin", "false");
				session = request.getSession();
				session.setAttribute("userID", userTarget);
				session.setAttribute("isLoggedIn", true);
				session.setAttribute("remoteAdmin", userAdmin);
				session.setAttribute("isAdmin", "true");
			} else{
				request.setAttribute("user", userAdmin);
				request.setAttribute("failedLogin", "true");
				rd = sc.getRequestDispatcher("/remoteAdmin.jsp");
				rd.forward(request, response);
			}
		} catch (SQLException e) {
			System.out.println("SQL exception thrown when lookingfor entered user");
			System.out.println("user likely to not exist");
			request.setAttribute("failedLogin", "true");
			rd = sc.getRequestDispatcher("/remoteAdmin.jsp");
			rd.forward(request, response);
			e.printStackTrace();
		}
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
