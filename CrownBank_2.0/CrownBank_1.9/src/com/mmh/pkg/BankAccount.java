package com.mmh.pkg;

import java.math.BigDecimal;

public class BankAccount {
	private int accountnumber;
	private BigDecimal balance;
	private BigDecimal intrest;
	private String currency;
	private String accountname;
	
	public int getAccountnumber() {
		return accountnumber;
	}
	public void setAccountnumber(int accountnumber) {
		this.accountnumber = accountnumber;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public BigDecimal getIntrest() {
		return intrest;
	}
	public void setIntrest(BigDecimal intrest) {
		this.intrest = intrest;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getAccountname() {
		return accountname;
	}
	public void setAccountname(String accountName) {
		this.accountname = accountName;
	}
	}
