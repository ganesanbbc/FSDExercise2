package com.workout.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TransactionsRepoTest {

	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private TransactionsRepo repo;
	
	
	@Test
	public void testFindByWorkId() {
		Cache WorkoutTxns = this.cacheManager.getCache("WorkoutTransactions");
		assertThat(WorkoutTxns).isNotNull();
		WorkoutTxns.clear();
		assertThat(WorkoutTxns.get("sandy@cts.com")).isNull();
		Iterable<Transactions> WorkoutTxnsRes = this.repo.findByWorkId((long)2);
		assertNotNull(WorkoutTxnsRes);
	}

}
