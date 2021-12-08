package com.rewards.points.model;

import java.util.List;

import lombok.Data;

@Data
public class RewardsInfo {

	private int customerId;
	private int totalRewards;
	private List<MonthlyRewardsInfo> monthlyRewardsInfo;
}
