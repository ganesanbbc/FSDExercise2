package com.workout.transaction;

import static org.junit.Assert.assertNotNull;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.workout.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.google.gson.Gson;
import com.workout.WorkoutApplication;
import com.workout.api.WorkOut;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = WorkoutApplication.class)
@WebMvcTest(value = TransactionController.class, secure = false)
public class TransactionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	TransactionController controller;

	@MockBean
	TransactionService service;

	User sampleUser = new User((long) 1, "password", "userName");
	WorkOut workout = new WorkOut((long) 1, (double) 123, "Jumping", sampleUser);
	Transactions workoutTxn = new Transactions((long) 1, LocalDateTime.now(), LocalDateTime.now(),
			workout);
	

	@Before
	public void setUp() throws Exception {
		this.mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCalculateduration() throws Exception {
		given(service.updateWorkoutTxnDetails(Mockito.any(Transactions.class))).willReturn(workoutTxn);
		String content = new Gson().toJson(workoutTxn);
		mockMvc.perform(post("/transaction").content(content).contentType(APPLICATION_JSON_UTF8)).andReturn();
	}
	
	@Test
	public void testGetWorkoutTxnDetails() {
		List<Transactions> response= new ArrayList<>();
		response.add(workoutTxn);
		given(service.getWorkoutTxnDetails(workout.getWorkoutId())).willReturn(response);
		List<Transactions> workoutTxnList= service.getWorkoutTxnDetails((long) 1);
		assertNotNull(workoutTxnList);
	}

}
