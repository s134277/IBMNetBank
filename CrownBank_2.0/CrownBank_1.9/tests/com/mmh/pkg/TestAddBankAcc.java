package com.mmh.pkg;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestAddBankAcc {
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
		
		// Creates the user:
		cont.CreateAcc(user, con);
	}
	
	@Test
	public void addBankAccSuccess() throws ClassNotFoundException, SQLException{
		/**
		 * Descrition: Tests that a bank account cen be created
		 */
		
		String currency = user.getCurrency();
		String username = user.getUsername();
		
		String result = cont.CreateBankAcc(currency, username, con);
		String[] results = result.split(";");
		int success = Integer.parseInt(results[0]);
		int accNumber = Integer.parseInt(results[1]);
		// Asserts that the Database returned 1 (success):
		assertEquals(success,1);
		
		
		// Cleans up by deleting the created account:
		success = cont.deleteBankAcc(accNumber,con);
		assertEquals(success,1);
		
		
	}
	
	@Test
	public void addBankAccFail() throws ClassNotFoundException, SQLException{
		/**
		 * Descrition: Ensures that the bank account is not created if 
		 * invalid input is provided. Invalid input is a wrong currency
		 */
		
		String username = user.getUsername();
		String currency = "nnn";
		String result = null;
		
		result = cont.CreateBankAcc(currency, username, con);
		
		String[] results = result.split(";");
		int success = Integer.parseInt(results[0]);
		int accNumber;
		String errorMSG = null;
		if(success == 1){
			accNumber = Integer.parseInt(results[1]);
			cont.deleteBankAcc(accNumber,con);
		} else{
			errorMSG = results[1];
		}
		// Asserts that the Database returned 1 (success):
		assertEquals(success,0);
		assertEquals(errorMSG,"An sql exception was thrown by the database");

	}
	
	@After
	public void cleanUp() throws ClassNotFoundException, SQLException{
		
		cont.deleteuser(user.getUsername(), con);
		
	}
	
}
