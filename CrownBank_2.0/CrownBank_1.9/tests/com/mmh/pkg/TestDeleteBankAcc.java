package com.mmh.pkg;

// USe case 7

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class TestDeleteBankAcc {
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
		
		String result = cont.CreateBankAcc(user.getCurrency(), user.getUsername(), con);
		String[] results = result.split(";");
		accNumber = Integer.parseInt(results[1]);
	}
	
	@Test
	public void testDeleteBankAccSuccess() throws ClassNotFoundException, SQLException{
		/**
		 * Description: Creates a user account, add an empty bank account 
		 * and delete it, ensure that the bank account no longer exists
		 */
		
		// tests that the bank account can be deleted:
		int success = cont.deleteBankAcc(accNumber,con);
		assertEquals(success,1);
	}
	
	@Test
	public void testDeleteBankAccFail() throws ClassNotFoundException, SQLException{
		/**
		 * Description: Creates a user account, adds empty bank account, 
		 * adds money to the account, attempts to delete the bank account
		 * ensures that the fail mechanism works properly.
		 */
		
		String depositSuccess = cont.deposit(accNumber, new BigDecimal(20.00), user.getCurrency(), con);
		assertEquals(depositSuccess,"1");
		int success = cont.deleteBankAcc(accNumber,con);
		assertEquals(success,0);
		
		String withdrawSuccess = cont.withdraw(accNumber, new BigDecimal(20.00), con);
		assertEquals(withdrawSuccess,"1");
		success = cont.deleteBankAcc(accNumber, con);
		assertEquals(success,1);
		
	}
}
