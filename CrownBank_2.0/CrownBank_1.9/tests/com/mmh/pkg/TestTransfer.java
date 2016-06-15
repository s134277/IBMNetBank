package com.mmh.pkg;

// Use case 8

import static org.junit.Assert.*;

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
	public void testTransferLocalSuccess(){
		/**
		 * Description: tests for successful transfer between 
		 * two bank accounts belonging to the same user account
		 * Also tests that two local accounts with different currencies 
		 * successfully transfers the appropirate amounts
		 */
	}
	
	@Test
	public void testTransferLocalFail(){
		/**
		 * Description: Tests that transactions fail if sending account 
		 * attempts to transfer more than the account balance aswell as 
		 * failing if attempting to transfer to an account number that 
		 * does not exist
		 */
	}
	
	@Test
	public void testTransferRemoteSuccess(){
		/**
		 * Description: Tests the same as the local version, but between 
		 * two accounts.
		 */
	}
	
	@Test
	public void testTransferRemoteFail(){
		/**
		 * Description: Tests the same as the local version, but between 
		 * two accounts.
		 */
	}
}
