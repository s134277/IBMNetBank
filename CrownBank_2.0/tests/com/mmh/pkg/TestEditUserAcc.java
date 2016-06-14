package com.mmh.pkg;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class TestEditUserAcc {
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
	public void testEditUserAccSuccess() throws ClassNotFoundException, SQLException{
		/**
		 * Description: Tests that we can change the following parameters:
		 * 1: Telephone number
		 * 2: postal number
		 * 3: password
		 * 4: currency
		 */
		
		// Setting up new data to edit the user:
		user.setTelephoneNumber(112);
		user.setPostnumber(3300);
		user.setPassword("newPassword");
		user.setCurrency("DKK");
		
		// Editing the account info:
		cont.editUserAccount(user,con);
		
		// Getting the user from the database and asserting if the values are changed:
		userData userEdited = cont.getUserInfo(user.getUsername(), con);
		assertEquals(userEdited.getTelephoneNumber(),112);
		assertEquals(userEdited.getPostnumber(),3300);
		assertEquals(userEdited.getPassword(),"newPassword");
		assertEquals(userEdited.getCurrency(),"DKK");
	}
	
	@Test
	public void testEditUserAccFail() throws SQLException{
		/**
		 * Description: The two parameters that can be invalid are postnumbers and currencies, 
		 * we test if SQL exceptions are properly thrown if theese are set wrongly:
		 */
		
		
		// Setting up new data to edit the user:
		// Postnumber 0000 is invalid and should cause en SQL exception to be thrown
		user.setTelephoneNumber(112);
		user.setPostnumber(0000);
		user.setPassword("newPassword");
		user.setCurrency("DKK");
		
		// Editing the account info:
		try {
			cont.editUserAccount(user,con);
		} catch (ClassNotFoundException | SQLException e) {
			// Checking the nothing changed:
			userData userEdited = cont.getUserInfo(user.getUsername(), con);
			assertEquals(userEdited.getTelephoneNumber(),911);
			assertEquals(userEdited.getPostnumber(),2800);
			assertEquals(userEdited.getPassword(),"testPW");
			assertEquals(userEdited.getCurrency(),"USD");		
		}
		
		user.setPostnumber(3300);
		user.setCurrency("notACurrency");
		
		try {
			cont.editUserAccount(user,con);
		} catch (ClassNotFoundException | SQLException e) {
			// Checking the nothing changed:
			userData userEdited = cont.getUserInfo(user.getUsername(), con);
			assertEquals(userEdited.getTelephoneNumber(),911);
			assertEquals(userEdited.getPostnumber(),2800);
			assertEquals(userEdited.getPassword(),"testPW");
			assertEquals(userEdited.getCurrency(),"USD");		
		}
		
	}
}
