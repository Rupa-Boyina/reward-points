package com.rewards.points;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class RewardPointsApplicationTests {

	@Autowired
	protected MockMvc mvc;
	
	private String fileName = "/sample.json";
	
	@Test
	void testRewardsSummaryRes() throws Exception {

		JSONArray data = (JSONArray) (new JSONParser()).parse(new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName))));
		MvcResult result = this.mvc.perform(MockMvcRequestBuilders.post("/rewards/getRewardsInfo")
				.content(data.toJSONString())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();

		String res = result.getResponse().getContentAsString();
		assertNotNull(res);
	}
	
	@Test
	void testTotalRewards() throws Exception {
		JSONArray data = (JSONArray) (new JSONParser()).parse(new BufferedReader(new InputStreamReader(this.getClass().getResourceAsStream(fileName))));
		this.mvc.perform(MockMvcRequestBuilders.post("/rewards/getRewardsInfo")
				.content(data.toJSONString())
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].totalRewards").value("762"))
				.andReturn();
	}
	
	@Test
	void testBadRequest() throws Exception {

		this.mvc.perform(MockMvcRequestBuilders.post("/rewards/getRewardsInfo")
				.content("")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().is4xxClientError())
				.andReturn();
	}

}
