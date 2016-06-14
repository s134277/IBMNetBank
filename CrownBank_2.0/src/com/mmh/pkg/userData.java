package com.mmh.pkg;

public class userData {
	private String username;
	private String password;
	private String fullName;
	private int postnumber;
	private int telephoneNumber;
	private boolean isAdmin;
	private String currency;
	private String type;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public int getPostnumber() {
		return postnumber;
	}
	public void setPostnumber(int postnumber) {
		this.postnumber = postnumber;
	}
	public int getTelephoneNumber() {
		return telephoneNumber;
	}
	public void setTelephoneNumber(int telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}
	public boolean isAdmin() {
		return isAdmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public void printAll(){
		
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		System.out.println("full name: "+ fullName);
		System.out.println("postnumber: " + postnumber);
		System.out.println("telephone: " + telephoneNumber);
		System.out.println("isAdmin: " + isAdmin);
		System.out.println("currency: " + currency);
		System.out.println("type: " + type);
		
	}
	
}
