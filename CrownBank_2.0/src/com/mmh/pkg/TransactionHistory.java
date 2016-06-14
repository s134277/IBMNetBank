package com.mmh.pkg;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;

public class TransactionHistory {
	ArrayList<Integer> sender = new ArrayList<Integer>();
	ArrayList<Integer> recipient = new ArrayList<Integer>();
	ArrayList<BigDecimal> amount = new ArrayList<BigDecimal>();
	ArrayList<Timestamp> dateOfTransfer = new ArrayList<Timestamp>();
	BigDecimal rate;
	public TransactionHistory(){
		
	}
	
}
