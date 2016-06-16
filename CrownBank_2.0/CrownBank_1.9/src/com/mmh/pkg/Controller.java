package com.mmh.pkg;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.annotation.Resource;
import javax.sql.DataSource;

public class Controller {

	public String transaction(BigDecimal amount, 
			int sender, int recipient, String currency, Connection con)
			throws SQLException, ClassNotFoundException {
		CallableStatement cstmt = con.prepareCall("{CALL \"DTUGRP03\".Transfer(?,?,?,?,?)}");
		cstmt.setBigDecimal(1, amount);
		cstmt.setInt(2, sender);
		cstmt.setInt(3, recipient);
		cstmt.setString(4, currency);
		cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);
		cstmt.execute();
		return cstmt.getString(5);
	}
	
	public String withdraw(int accnum, BigDecimal amount, Connection con)throws SQLException, ClassNotFoundException{
		CallableStatement cstmt = con.prepareCall("{CALL \"DTUGRP03\".Withdraw(?,?,?)}");
		cstmt.setInt(1, accnum);
		cstmt.setBigDecimal(2, amount);
		cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
		cstmt.execute();
		return cstmt.getString(3);
	}
	public String deposit(int accnum, BigDecimal amount, String currency, Connection con)throws SQLException, ClassNotFoundException{
		CallableStatement cstmt = con.prepareCall("{CALL \"DTUGRP03\".Deposit(?,?,?,?)}");
		cstmt.setInt(1, accnum);
		cstmt.setBigDecimal(2, amount);
		cstmt.setString(3, currency);
		cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
		cstmt.execute();
		return cstmt.getString(4);
	}
	
	public ArrayList<TransactionBean> transactionHistory(int bankAcc, String currency, Connection con)
			throws SQLException, ClassNotFoundException {
		String rateQuery = "SELECT \"Rate\" FROM \"DTUGRP03\".\"Valuta\" " + "WHERE '" + currency + "' = \"Currency\"";
		String sqlQuery = "SELECT \"BankAccount_Sender\",\"BankAccount_Recipient\", " + "\"Amount\", \"Date\" "
				+ "FROM \"DTUGRP03\".\"TransactionHistory\" " + "WHERE " + bankAcc + " = \"BankAccount_Sender\""
				+ " OR " + bankAcc + " = \"BankAccount_Recipient \"";

		Statement stmt = con.createStatement();
		ResultSet rateset = stmt.executeQuery(rateQuery);
		ArrayList<TransactionBean> transHist = new ArrayList<TransactionBean>();
		BigDecimal rate = null;
		while (rateset.next()) {
			rate = rateset.getBigDecimal(1);
		}
		rateset.close();

		ResultSet rs = stmt.executeQuery(sqlQuery);
		BigDecimal bg = new BigDecimal("100.0");
		while (rs.next()) {
			TransactionBean transaction = new TransactionBean();
			transaction.setSender(rs.getInt(1));
			transaction.setRecipient(rs.getInt(2));
			transaction.setAmount(rs.getBigDecimal(3).multiply(bg).divide(rate,4, RoundingMode.HALF_UP));
			String datetime = rs.getTimestamp(4).toString();
			String date = datetime.substring(0, 10);
			String time = datetime.substring(10,17);
			transaction.setDate(date);
			transaction.setTime(time);
			transHist.add(transaction);
		}
		rs.close();
		return transHist;
	}

	public int deleteuser(String userName, Connection con) throws SQLException, ClassNotFoundException {
		CallableStatement cstmt = con.prepareCall("{CALL \"DTUGRP03\".DeleteUser(?,?)}");
		cstmt.setString(1, userName);
		cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
		cstmt.execute();
		return cstmt.getInt(2);
	}
	
	public int deleteBankAcc(int AccNumber,Connection con) 
			throws SQLException, ClassNotFoundException {
		CallableStatement cstmt = con.prepareCall("{CALL \"DTUGRP03\".DeleteBankAcc(?,?)}");	
		cstmt.setInt(1, AccNumber);
		cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
		try{
			cstmt.execute();
		} catch(SQLException e){
			return 0;
		}
		
		return cstmt.getInt(2);
	}
	
	public userData getUserInfo(String username, Connection con) throws SQLException {
		
		String sqlQuery = "SELECT * FROM \"DTUGRP03\".\"UserAccount\" WHERE \"UserName\" = " + "'"+username+"'";
		
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sqlQuery);
		userData user = new userData();

		user.setUsername(username);
		
		while (rs.next()){
			if(rs.getString(5).equals("Client")){
				user.setAdmin(false);
				user.setType("Client");
			}else{
				user.setAdmin(true);
				user.setType("Administrator");
			}
		user.setFullName(rs.getString(2));
		user.setTelephoneNumber(rs.getInt(3));
		user.setPassword(rs.getString(4));
		user.setPostnumber(rs.getInt(6));
		user.setCurrency(rs.getString(7));
		}
		
		return user;
	}
	
	public ArrayList<String> getCurrencies(Connection con) throws SQLException, ClassNotFoundException{
		String sqlQuery = "SELECT \"Currency\" FROM \"DTUGRP03\".\"Valuta\"";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sqlQuery);
		ArrayList<String> currencies = new ArrayList<String>();
		while (rs.next()){
			currencies.add(rs.getString(1));
		}
		return currencies;
	}
	
	public void editUserAccount(userData user, Connection con) throws ClassNotFoundException, SQLException{
		
		String sqlQuery = "UPDATE \"DTUGRP03\".\"UserAccount\" "
				+ "SET \"TelephoneNumber\" = "+user.getTelephoneNumber()+ ", \"City_Postnumber\" = "+user.getPostnumber()
				+ ", \"Password\" = '"+user.getPassword()+"', \"Valuta_Currency\" = '"
				+user.getCurrency()+"' WHERE \"UserName\" = '"+user.getUsername()+"'";
	Statement stmt = con.createStatement();
	stmt.executeUpdate(sqlQuery);
	}
	
	
	public ArrayList<BankAccount> getBankAcc(String username, Connection con)
			throws ClassNotFoundException, SQLException {
		String sqlQuery = "SELECT \"AccountNumber\", \"Balance\", " + "\"Intrest\", \"Valuta_Currency\" FROM "
				+ "\"DTUGRP03\".\"BankAccount\" INNER JOIN "
				+ "\"DTUGRP03\".\"AccountAssociativity\" ON \"AccountNumber\" = "
				+ "\"BankAccount_AccountNumber\" WHERE \"UserAccount_UserName\" = '" + username + "'";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(sqlQuery);
		ArrayList<BankAccount> bankaccs = new ArrayList<BankAccount>();
		while (rs.next()) {
			BankAccount acc = new BankAccount();
			acc.setAccountnumber(rs.getInt(1));
			acc.setBalance(rs.getBigDecimal(2));
			acc.setIntrest(rs.getBigDecimal(3));
			acc.setCurrency(rs.getString(4));
			bankaccs.add(acc);
		}
		return bankaccs;
	}
	
	public void editBankAccount(int accNumber,BigDecimal intrest, String currency, Connection con) throws ClassNotFoundException, SQLException{
		String sqlQuery = "UPDATE \"DTUGRP03\".\"BankAccount\" "
				+ "SET \"Intrest\" = "+intrest+", \"Valuta_Currency\" = '"+currency
				+"' WHERE \"AccountNumber\" = "+accNumber;
		Statement stmt = con.createStatement();
		stmt.executeUpdate(sqlQuery);
	}

	public void updateBankAccounts(ArrayList<BankAccount> bankaccs, Connection con) throws ClassNotFoundException, SQLException {
		Statement stmt = con.createStatement();
		for (int i = 0; i < bankaccs.size(); i++) {
			//Fetches the rate for the currencies in the database
			String sqlQuery = "SELECT \"rate\" FROM \"DTUGRP03\".\"City\" WHERE \"Currency\" = '"+bankaccs.get(i)+"'";
			ResultSet rs = stmt.executeQuery(sqlQuery);
			BigDecimal bg = new BigDecimal("100.0");
			while (rs.next()) {
				//Does the rate calculation on the current balance.
				BigDecimal rate = rs.getBigDecimal(1);
				BigDecimal currentbalance = bankaccs.get(i).getBalance();
				bankaccs.get(i).setBalance(currentbalance.multiply(bg).divide(rate,4, RoundingMode.HALF_UP));
			}
			rs.close();
		}
	stmt.close();
	}

	public int Login(String login, String password, Connection con) {
		
		CallableStatement cstmt;
		try {
			cstmt = con.prepareCall("{CALL \"DTUGRP03\".Verification(?,?,?)}");
			cstmt.setString(1, login);
		 	cstmt.setString(2, password);
		 	cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
		 	cstmt.execute();
		 	return cstmt.getInt(3);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		} 
		
	}

	public String CreateAcc(userData user, Connection con) {
		CallableStatement cstmt;
		int result = 0;
		
		try {
			cstmt = con.prepareCall("{CALL \"DTUGRP03\".CreateUserAccount(?,?,?,?,?,?,?,?,?)}");
			cstmt.setString(1, user.getUsername());
		 	cstmt.setString(2, user.getFullName());
		 	cstmt.setInt(3, user.getTelephoneNumber());
		 	cstmt.setString(4, user.getPassword());
		 	cstmt.setString(5, user.getType());
		 	cstmt.setInt(6, user.getPostnumber());
		 	cstmt.setString(7, user.getCurrency());
		 	cstmt.registerOutParameter(8, java.sql.Types.VARCHAR);
		 	cstmt.registerOutParameter(9, java.sql.Types.INTEGER);
		 	cstmt.execute();

		 	result = cstmt.getInt(9);
		 	String status = cstmt.getString(8);
		 	
		 	return result + ";" + status;
		 	
		} catch (SQLException e) {
			String status = "An sql exception was thrown by the database";
			e.printStackTrace();
			return result + ";" + status;
		} 
	}

	public String CreateBankAcc(String currency, String userID, Connection con) {
		CallableStatement cstmt;
		try {
			cstmt = con.prepareCall("{CALL \"DTUGRP03\".CreateBankAccount(?,?,?,?)}");
			cstmt.setString(1, userID);
		 	cstmt.setString(2, currency);
		 	cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
		 	cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
		 	cstmt.execute();
		 	
		 	return cstmt.getInt(3) + ";" + cstmt.getInt(4);
		 	
		} catch (SQLException e) {
			String status = "An sql exception was thrown by the database";
			return 0 + ";" + status;
		} 
	}

	public String AdminCheck(String login, String password, Connection con) throws SQLException {
		userData data = getUserInfo(login, con);
		if(data.isAdmin() && data.getPassword().equals(password)){
			return "true";
		}
		return "false";
	}
}

