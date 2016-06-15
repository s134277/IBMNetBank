package com.mmh.pkg;

// USe case 10

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTransactionHistory {
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
	public void testViewTransactionsSuccess(){
		/**
		 * Description: Generates a bank account with a transaction 
		 * and verifies that the transaction history corresponds with 
		 * the transaction
		 */
		
		try {
			cont.deposit(accNumber, new BigDecimal(50.00), "USD", con);
			//cont.deposit(accNumber, new BigDecimal(100.00), "USD", con);
		} catch (ClassNotFoundException | SQLException e1) {
			fail();
		}
		
		ArrayList<TransactionBean> transHist = null;
		try {
			transHist = cont.transactionHistory(accNumber, user.getCurrency(), con);
		} catch (ClassNotFoundException | SQLException e) {
			fail();
		}
		String result1 = transHist.get(0).getAmount().toString();
		//String result2 = transHist.get(1).getAmount().toString();
		assertEquals(result1,"50.0000");
		//assertEquals(result2,"100.0000");
		
		try {
			cont.withdraw(accNumber, new BigDecimal(50.0000), con);
			cont.deleteBankAcc(accNumber, con);
		} catch (ClassNotFoundException | SQLException e) {
			fail();
		}
	
	}
}
