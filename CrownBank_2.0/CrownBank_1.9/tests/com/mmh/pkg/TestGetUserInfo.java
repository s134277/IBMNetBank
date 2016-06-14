package com.mmh.pkg;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class TestGetUserInfo {
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
	public void testGetUserInfoSuccess() throws SQLException{
		/**
		 * Description: Verifies that the helper method getUserInfo() in the controller
		 * actually works. This method is essential for many functionalities 
		 * but is not directly linked to a user case
		 * 
		 */
		// Tests that the userInfo can be retrieved and that the values are correct:
		userData user2 = cont.getUserInfo(user.getUsername(), con);
		assertEquals(user2.getCurrency(),user.getCurrency());
		assertEquals(user2.getFullName(),user.getFullName());
		assertEquals(user2.getPassword(),user.getPassword());
		assertEquals(user2.getPostnumber(),user.getPostnumber());
		assertEquals(user2.getTelephoneNumber(),user.getTelephoneNumber());
		assertEquals(user2.getType(),user.getType());
		assertEquals(user2.getUsername(),user.getUsername());
	}
}
