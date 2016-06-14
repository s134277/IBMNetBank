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


@WebServlet("/EditAcc")
public class EditAcc extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name="jdbc/exampleDS")
	private	DataSource ds1;
       

    public EditAcc() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext sc = this.getServletContext();
		RequestDispatcher rd = sc.getRequestDispatcher("/editAcc.jsp");
		
		Connection con = null;
		String conUser = "DTU07";
		String conPassword = "FAGP2016";
		try{
			con=ds1.getConnection(conUser,conPassword);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		String userID = (String) request.getSession().getAttribute("userID");
		Controller control = new Controller();
		userData user = null;
		try {
			user = control.getUserInfo(userID,con);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String password = user.getPassword();
		String reqPassword = (String)request.getParameter("password1");
		if(!reqPassword.isEmpty() && reqPassword!="" && reqPassword!=null){
			String reqPassword2 = (String)request.getParameter("password2");
			if(reqPassword.equals(reqPassword2)){
				password = reqPassword;
			}else{
				request.setAttribute("success", "false");
				rd.forward(request, response);
			}
		}
		int tel = user.getTelephoneNumber();
		String reqTel = (String)request.getParameter("tel");
		if(!reqTel.isEmpty() && reqTel!= "" && reqTel!=null){
			tel = Integer.parseInt(reqTel);
		} 
		int post = user.getPostnumber();
		String reqPost = (String)request.getParameter("post");
		if(!reqPost.isEmpty() && reqPost!= "" && reqPost!=null){
			post = Integer.parseInt(reqPost);
		}
		
		user.setCurrency(request.getParameter("currency"));
		user.setPassword(password);
		user.setTelephoneNumber(tel);
		user.setPostnumber(post);
		
		try {
			control.editUserAccount(user, con);
			request.setAttribute("success", "true");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			request.setAttribute("success", "false");
		}
		
		rd.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
