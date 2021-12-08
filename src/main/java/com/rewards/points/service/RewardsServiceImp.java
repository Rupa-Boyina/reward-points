package com.rewards.points.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.stereotype.Service;

import com.rewards.points.model.IndividualRewards;
import com.rewards.points.model.MonthlyRewardsInfo;
import com.rewards.points.model.RewardsInfo;
import com.rewards.points.model.TransactionInfo;

@Service
public class RewardsServiceImp implements RewardsService {

	@Override
	public List<RewardsInfo> getRewardsInfo(List<TransactionInfo> transactionInfoList) {

		List<IndividualRewards> individualRewardsList = new ArrayList<>();

		Map<Integer, Map<Integer, List<TransactionInfo>>> result = new HashMap<Integer, Map<Integer, List<TransactionInfo>>>();

		for (TransactionInfo transactionInfo : transactionInfoList) {
			IndividualRewards individualRewards = new IndividualRewards();
			individualRewards.setIndividualReward(calculateRewards((int) transactionInfo.getAmount()));
			individualRewards.setCustomerId(transactionInfo.getCustomerId());
			individualRewardsList.add(individualRewards);
			transactionInfo.setRewards(calculateRewards((int) transactionInfo.getAmount()));
			int month = transactionInfo.getTransactionDate()!=null ? transactionInfo.getTransactionDate().getMonth():0;
			Map<Integer, List<TransactionInfo>> custMap = result.getOrDefault(transactionInfo.getCustomerId(), new HashMap<Integer, List<TransactionInfo>>());
			result.put(transactionInfo.getCustomerId(), custMap);
			List<TransactionInfo> monthlist = custMap.getOrDefault(month, new ArrayList<TransactionInfo>());
			monthlist.add(transactionInfo);
			custMap.put(month, monthlist);
		}

		List<RewardsInfo> resultList = new ArrayList<>();
		for (Entry<Integer, Map<Integer, List<TransactionInfo>>> value : result.entrySet()) {
			RewardsInfo rInfo = new RewardsInfo();
			rInfo.setCustomerId(value.getKey());
			rInfo.setMonthlyRewardsInfo(new ArrayList<MonthlyRewardsInfo>());
			for (Entry<Integer, List<TransactionInfo>> value1 : value.getValue().entrySet()) {
				MonthlyRewardsInfo monthRewardsInfo = new MonthlyRewardsInfo();
				monthRewardsInfo.setMonth(value1.getKey());
				for (TransactionInfo value2 : value1.getValue()) {
					monthRewardsInfo.setMonthlyRewards(monthRewardsInfo.getMonthlyRewards() + value2.getRewards());
				}
				rInfo.setTotalRewards(rInfo.getTotalRewards() + monthRewardsInfo.getMonthlyRewards());
				rInfo.getMonthlyRewardsInfo().add(monthRewardsInfo);
			}
			resultList.add(rInfo);
		}

		return resultList;
	}

	private int calculateRewards(int price) {
		if (price >= 50 && price < 100) {
			return price - 50;
		} else if (price > 100) {
			return (2 * (price - 100) + 50);
		}
		return 0;
	}

}
