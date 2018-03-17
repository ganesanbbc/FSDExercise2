package com.workout.transaction;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

	@Autowired
	private TransactionsRepo repo;
	
	
	@CachePut(value = "transactions", unless="#result==null")
	public Transactions updateWorkoutTxnDetails(Transactions transactions) {
		repo.save(transactions);
		return transactions;
	}
	
	@Cacheable(value = "transactions")
	public List<Transactions> getWorkoutTxnDetails(Long workoutId) {
		return repo.findByWorkId(workoutId);
	}

}
