package com.workout.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.workout.user.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	String SELECT_USER = "SELECT US.userId FROM User US WHERE US.userName= :userName";

	@Query(SELECT_USER)
	Long findByEmailId(@Param("userName") String userName);

}
