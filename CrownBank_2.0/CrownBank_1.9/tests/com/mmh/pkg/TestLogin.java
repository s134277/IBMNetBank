package com.mmh.pkg;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class TestLogin {
	private Connection con = null;
	private String url = "jdbc:db2://192.86.32.54:5040/"
			+ "DALLASB:retrieveMessagesFromServerOnGetMessage=true;"
			+ "emulateParameterMetaDataForZCalls=1;";
	private String driverName = "com.ibm.db2.jcc.DB2Driver";
	private Controller cont = new Controller();
	private userData user = new userData();
	
	@Before
	public void setUp() throws ClassNotFoundException, SQLException{
		// Generating a connection
		Properties properties = new Properties();
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
		properties.put("user", "DTU06");
		properties.put("password", "FAGP2016");
		try {
			con = DriverManager.getConnection(url, properties);
		} catch (SQLException e) {
			e.printStackTrace();
		}
						
		// Preparing data:
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
		// Creates the user for the following tests:
		cont.CreateAcc(user, con);
	}
	
	@Test
	public void TestLoginSuccess(){
		/**
		 * Description: Tests that a user account named "JUNITLogin"
		 * can be verified on the databse, if so, the DB and hence the 
		 * controller returns the int "1". 
		 * Verification is the method used for validating user credentials 
		 * when logging in.
		 */
		
		int result = cont.Login(user.getUsername(), user.getPassword(), con);
		
		assertEquals(result,1);
	}
	
	@Test
	public void TestLoginFail(){
		/**
		 * Description: Tests that a user account named "JUNITLogin"
		 * can't be verified on the databse if given the wrong password,
		 * or if given a non-existing username, if so, the DB and hence the 
		 * controller returns the int "0". 
		 */
		
		user.setPassword("wrongPassword");
		int result1 = cont.Login(user.getUsername(), user.getPassword(), con);
		assertEquals(result1,0);
		
		// Sets the username to a user that doesn't exist:
		user.setUsername("NonExistingUser");
		// Resets the password:
		user.setPassword("testPW");
		int result2 = cont.Login(user.getUsername(), user.getPassword(), con);
		assertEquals(result2,0);
		
	}
}
