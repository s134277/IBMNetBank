package com.mmh.pkg;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class TestBankAccounts {
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
	public void testGetCurrencies() throws ClassNotFoundException, SQLException{
		/**
		 * Description: Tests that the standard currency USD can be retirved
		 * This ensures that the getCurrencies method works
		 */
		ArrayList<String> currencies = cont.getCurrencies(con);
		boolean success = false;
		
		if(currencies.contains("USD")) success = true;
		assertTrue(success);
	}
	
	@Test
	public void testGetPostnumbers() throws ClassNotFoundException, SQLException{
		/**
		 * Description: Tests that postnumbers can be retirved
		 * This ensures that the getPostnumbers method works
		 */
		ArrayList<String> postnumbers = cont.getPostnumbers(con);
		boolean success = false;
		
		if(postnumbers.contains("2800")) success = true;
		assertTrue(success);
	}
	
	@Test
	public void testGetBankAcc() throws ClassNotFoundException, SQLException{
		/**
		 * Description: Tests that getBankAcc is empty if the user has no bankaccounts yet
		 * Tests that the list is non-empty after adding an account
		 * Tests that the list becomes empty after deleting the account
		 */
		ArrayList<BankAccount> bankaccs = cont.getBankAcc(user.getUsername(), con);
		assertTrue(bankaccs.isEmpty());
		
		String result = cont.CreateBankAcc(user.getCurrency(), user.getUsername(), con);
		String[] results = result.split(";");
		int accNumber = Integer.parseInt(results[1]);
		bankaccs = cont.getBankAcc(user.getUsername(), con);
		assertTrue(!bankaccs.isEmpty());
		
		cont.deleteBankAcc(accNumber, con);
		bankaccs = cont.getBankAcc(user.getUsername(), con);
		assertTrue(bankaccs.isEmpty());
		
	}
	
	
}
