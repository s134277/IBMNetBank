package com.mmh.pkg;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Before;

public class DataSetup {
	
	//@Before
	public void setUpConnection() throws ClassNotFoundException, SQLException{
		
		// Generating a connection
		Connection con = null;
		Properties properties = new Properties();
		String url = "jdbc:db2://192.86.32.54:5040/"
				+ "DALLASB:retrieveMessagesFromServerOnGetMessage=true;"
				+ "emulateParameterMetaDataForZCalls=1;";
				try {
					Class.forName("com.ibm.db2.jcc.DB2Driver");
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		properties.put("user", "DTU06");
		properties.put("password", "FAGP2016");
		try {
			con = DriverManager.getConnection(url, properties);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Initialising controller:
		Controller cont = new Controller();
		
		// Preparing data:
		userData user = new userData();
		user.setAdmin(true);
		user.setCurrency("USD");
		user.setFullName("Test User");
		user.setPassword("testPW");
		user.setPostnumber(2800);
		user.setTelephoneNumber(911);
		user.setType("Administrator");
		user.setUsername("JUNITLogin");
		
		// Ensures there is no old user with this username:
		cont.deleteuser(user.getUsername(), con);
		
		// Stores the user in the DB:
		cont.CreateAcc(user, con);
		
		
		
	}
	
}
