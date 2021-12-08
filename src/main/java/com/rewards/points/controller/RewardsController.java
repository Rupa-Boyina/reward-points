package com.rewards.points.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rewards.points.model.RewardsInfo;
import com.rewards.points.model.TransactionInfo;
import com.rewards.points.service.RewardsService;

@RestController
@RequestMapping("/rewards")
public class RewardsController {

	@Autowired
	private RewardsService rewardsService;
	
	@PostMapping("/getRewardsInfo")
	@ResponseBody
	public List<RewardsInfo> getRewardsInfo(@RequestBody List<TransactionInfo> transactionInfo) {
		return rewardsService.getRewardsInfo(transactionInfo);
	}
}
