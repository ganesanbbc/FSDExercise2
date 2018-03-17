package com.workout.transaction;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TransactionsRepo extends CrudRepository<Transactions, Integer> {


    String SELECT_TRANS = "SELECT WT.txnId,WT.startTime,WT.stopTime,WT.duration,WT.calBurnt,WT.workout.workoutId " +
            "FROM Transactions WT " +
            "WHERE WT.workout.workoutId= :workoutId";

    @Query(SELECT_TRANS)
    List<Transactions> findByWorkId(@Param("workoutId") Long workoutId);

}
