package com.workout.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;

import com.workout.user.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {
 
	@Autowired
    private UserRepository repository;
	
	@Autowired
	private CacheManager cacheManager;
	
	@Test
	public void testFindByEmailId() {
		Cache users = this.cacheManager.getCache("user");
		assertThat(users).isNotNull();
		users.clear();
		assertThat(users.get("example@example.com")).isNull();
		Long be = this.repository.findByEmailId("example@example.com");
		assertNotNull(be);
	}

}
