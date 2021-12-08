package com.rewards.points.model;

import java.util.Date;

import lombok.Data;

@Data
public class TransactionInfo {

	private int customerId;
	private float amount;
	private Date transactionDate;
	private int rewards;
}
