package com.workout.api;

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
public class WorkoutRepositoryTest {
	
	@Autowired
	private CacheManager cacheManager;
	
	@Autowired
	private WorkoutRepository repository;

	@Test
	public void testFindByUserId() {
		Cache worekouts = this.cacheManager.getCache("Workout");
		assertThat(worekouts).isNotNull();
		worekouts.clear();
		Iterable<WorkOut> response = this.repository.findByUserId((long)1);
		assertNotNull(response);
	}

}
