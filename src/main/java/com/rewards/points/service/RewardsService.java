package com.rewards.points.service;

import java.util.List;

import com.rewards.points.model.RewardsInfo;
import com.rewards.points.model.TransactionInfo;

public interface RewardsService {
	
	public List<RewardsInfo> getRewardsInfo(List<TransactionInfo> transactionInfo);

}
