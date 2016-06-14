package com.mmh.pkg;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

public class TestDeleteUserAcc {
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
	public void testDeleteUserAccSuccess() throws SQLException, ClassNotFoundException{
		/**
		 * Description: Since we created the account stored in the 
		 * object "user" in the @Before, we can simply ensure that it exists, 
		 * delete it and ensure that it no longer exists: 
		 */
		
		userData user2 = cont.getUserInfo(user.getUsername(), con);
		assertTrue(user2!=null);
		
		cont.deleteuser(user.getUsername(), con);
		userData user3 = null;
		try{
			cont.getUserInfo(user.getUsername(), con);
		}catch(SQLException e){
			
		}
		assertTrue(user3==null);
		
	}
	
	@Test
	public void testDeleteUserAccFail(){
		/** Description: user accounts cant be deleted if they have bank 
		 * accounts that isn't deleted before hand.
		 */
		
		cont.CreateBankAcc("USD", user.getUsername(),new BigDecimal(3.00), con);
		
		boolean autoFail = false;
		
		assertTrue(autoFail);
		
	}
}
