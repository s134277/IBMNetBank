package com.mmh.pkg;
import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;


public class TestCreateUserAcc {
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
	}
	
	
	
	@Test
	public void testCreateUserSuccess() throws ClassNotFoundException, SQLException{
		
		/**
		 * Description: Tests that a user account named "JUNITLogin"
		 * can be created on the databse, if so, the DB and hence the 
		 * controller returns the string "1".
		 */
			
		// Stores the user in the DB:
		String returnVal = cont.CreateAcc(user, con);
		String[] results = returnVal.split(";");
		int result = Integer.parseInt(results[0]);
		assertEquals(result,1);
		
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
	
	@Test
	public void testCreateUserTaken() throws ClassNotFoundException, SQLException{
		
		/**
		 * Description: Tests that a user account named "JUNITLogin"
		 * can be created on the databse once, but when attempted to create again, 
		 * the username is taken and a return of 0 from DB2 as well a a status message 
		 * is displayed
		 */
		
		//Creates the user the first time: (succeeds)
		testCreateUserSuccess();
		
		//Creates the same user again:
		String returnVal = cont.CreateAcc(user, con);
		String[] results = returnVal.split(";");
		
		int result = Integer.parseInt(results[0]);
		String statusMSG = results[1];
		assertEquals(result,0);	
		assertEquals(statusMSG,"Username a");
	}
	
	@Test
	public void testCreateUserWrongInput() throws ClassNotFoundException, SQLException{
		
		/**
		 * Description: Tests that a user account named "JUNITLogin"
		 * is not created in the database when attempted to create with invalid input
		 * (a post number that doesnt exist)
		 */
		
		// Sets wrong input:
		user.setPostnumber(0000);
		
		String returnVal = cont.CreateAcc(user, con);
		String[] results = returnVal.split(";");
		
		int result = Integer.parseInt(results[0]);
		String statusMSG = results[1];
		assertEquals(result,0);	
		assertEquals(statusMSG,"City not e");
	}
	
	
}
