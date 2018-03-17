package com.workout.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	
	public User createUser(User user) {
		repository.save(user);
		return user;
	}
	
	public Long findByUserName(User user) {
		return repository.findByEmailId(user.getUserName());
	}

	public Long authenticateUser(String userName, String password) {
		Long userId = repository.findByEmailId(userName);

		if (userId != null)
			return userId;

		return null;
	}

}
