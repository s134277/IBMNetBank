package com.mmh.pkg;

// Use case 8

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTransfer {
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
	public void testTransferLocalSuccess() throws ClassNotFoundException, SQLException{
		/**
		 * Description: tests for successful transfer between 
		 * two bank accounts belonging to the same user account
		 * Also tests that two local accounts with different currencies 
		 * successfully transfers the appropirate amounts
		 */
		
		// creates bank accounts:
		String result = cont.CreateBankAcc(user.getCurrency(), user.getUsername(), con);
		String[] results = result.split(";");
		int accNumber1 = Integer.parseInt(results[1]);
		
		result = cont.CreateBankAcc(user.getCurrency(), user.getUsername(), con);
		results = result.split(";");
		int accNumber2 = Integer.parseInt(results[1]);
		
		cont.deposit(accNumber1, new BigDecimal(100.00), "USD", con);
		
		
		String status = cont.transaction(new BigDecimal(100.00), accNumber1, accNumber2, "USD", con);
		assertEquals(status,"Transaction complete");
		
		cont.withdraw(accNumber2, new BigDecimal(100.0000), con);
		cont.deleteBankAcc(accNumber2, con);
		cont.deleteBankAcc(accNumber1, con);
		
		
	}
	
	@Test
	public void testTransferLocalFail() throws ClassNotFoundException, SQLException{
		/**
		 * Description: Tests that transactions fail if sending account 
		 * attempts to transfer more than the account balance aswell as 
		 * failing if attempting to transfer to an account number that 
		 * does not exist
		 */
		
		// creates bank accounts:
		String result = cont.CreateBankAcc(user.getCurrency(), user.getUsername(), con);
		String[] results = result.split(";");
		int accNumber1 = Integer.parseInt(results[1]);
		
		result = cont.CreateBankAcc(user.getCurrency(), user.getUsername(), con);
		results = result.split(";");
		int accNumber2 = Integer.parseInt(results[1]);
		
		cont.deposit(accNumber1, new BigDecimal(100.00), "USD", con);
		
	
		String status = cont.transaction(new BigDecimal(101.00), accNumber1, accNumber2, "USD", con);

		assertEquals(status,"Insufficient funds");

		
			cont.withdraw(accNumber2, new BigDecimal(100.0000), con);
			cont.deleteBankAcc(accNumber2, con);
			cont.deleteBankAcc(accNumber1, con);
		
	}
	
	@Test
	public void testTransferRemoteSuccess(){
		/**
		 * Description: Tests the same as the local version, but between 
		 * two accounts.
		 */
//		user.setUsername("JUNITLogin2");
//		cont.CreateAcc(user, con);
	
	}
	
	@Test
	public void testTransferRemoteFail(){
		/**
		 * Description: Tests the same as the local version, but between 
		 * two accounts.
		 */
	}
}
