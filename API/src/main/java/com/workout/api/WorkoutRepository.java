package com.workout.api;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface WorkoutRepository extends CrudRepository<WorkOut, Integer> {

    String SELECT_WORKOUT = "SELECT WO.workoutId,WO.calBurntPerUnitTime,WO.title,WO.unitTime," +
            "WO.user.userName FROM WorkOut WO " +
            "WHERE WO.user.userId= :userId";

    @Query(SELECT_WORKOUT)
    List<WorkOut> findByUserId(@Param("userId") Long userId);

}
