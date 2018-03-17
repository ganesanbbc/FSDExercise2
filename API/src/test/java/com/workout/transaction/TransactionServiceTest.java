package com.workout.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

import java.time.LocalDateTime;
import java.util.List;

import com.workout.api.WorkOut;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import com.workout.user.User;

public class TransactionServiceTest {
	
	@InjectMocks
    private TransactionService service;

    @Mock
    private TransactionsRepo repository;
    
	RestTemplate restTeamplate= new RestTemplate();
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    User sampleUser = new User((long) 1, "password", "userName");
    WorkOut workout= new WorkOut((long)1, (double)123, "some_activity", sampleUser);
    
	Transactions workoutTxnResponse= new Transactions((long) 1,LocalDateTime.now(), LocalDateTime.now(),workout);
	Transactions workoutTxnInput= new Transactions((long) 1,LocalDateTime.now(), LocalDateTime.now(),workout);

	@Test
	public void testUpdateWorkoutDuration() {
		when(repository.save(workoutTxnInput)).thenReturn(workoutTxnResponse);
		Transactions respose=service.updateWorkoutTxnDetails(workoutTxnInput);
		assertThat(respose.equals(workoutTxnResponse));
	}
	
	@Test
	public void testGetWorkoutTxnDetails() {
		List<Transactions> workoutTxnList=service.getWorkoutTxnDetails((long) 1);
		assertNotNull(workoutTxnList);
	}
	
	@Test
	public void testMultiHitsGetWorkoutTxnDetails() throws Exception {
		MockRestServiceServer server = MockRestServiceServer.bindTo(restTeamplate).build();
		server.expect(ExpectedCount.manyTimes(), requestTo("/workoutTxn/1")).andExpect(method(HttpMethod.GET))
	     .andRespond(withSuccess());
	}

}
