package com.mmh.pkg;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

public class TransactionBean {
	private int sender;
	private int recipient;
	private BigDecimal amount;
	private String date;
	private String time;
	
	public TransactionBean(){
		
	}

	public int getSender() {
		return sender;
	}

	public void setSender(int sender) {
		this.sender = sender;
	}

	public int getRecipient() {
		return recipient;
	}

	public void setRecipient(int recipient) {
		this.recipient = recipient;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
}
