package com.workout.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WorkoutService {

    @Autowired
    private WorkoutRepository workoutRepo;

    public WorkOut defineWorkout(WorkOut workout) {
        workoutRepo.save(workout);
        return workout;

    }

    public List<WorkOut> getWorkoutDetails(Long userId) {
        return workoutRepo.findByUserId(userId);
    }
}
