package com.mmh.pkg;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

//Use case 9

public class TestEditBankAcc {
	private Connection con = null;
	private String url = "jdbc:db2://192.86.32.54:5040/"
			+ "DALLASB:retrieveMessagesFromServerOnGetMessage=true;"
			+ "emulateParameterMetaDataForZCalls=1;";
	private String driverName = "com.ibm.db2.jcc.DB2Driver";
	private Controller cont = new Controller();
	private userData user = new userData();
	private int accNumber;
	
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
		
		// creates bank account:
		String result = cont.CreateBankAcc(user.getCurrency(), user.getUsername(), con);
		String[] results = result.split(";");
		accNumber = Integer.parseInt(results[1]);
	}
	
	@Test
	public void testEditAccountSuccess() throws ClassNotFoundException, SQLException {
		/**
		 * Description: Tests that interest and currency can be 
		 * edited for a bank account.
		 */
		try {
			cont.editBankAccount(accNumber, new BigDecimal(5.00), "DKK", con);
		} catch (ClassNotFoundException | SQLException e) {
			fail();
		}
		
		cont.deleteBankAcc(accNumber, con);
	}
	
	@Test
	public void testEditAccountFail() throws ClassNotFoundException, SQLException{
		/**
		 * Description: Tests that editing interest and currency 
		 * fails if given an invalid currency or a negative interest
		 */
		
		try {
			cont.editBankAccount(accNumber, new BigDecimal(5.00), "yen", con);
			fail();
		} catch (ClassNotFoundException | SQLException e) {
			
		}
		
		int success = cont.deleteBankAcc(accNumber, con);
	}
}
