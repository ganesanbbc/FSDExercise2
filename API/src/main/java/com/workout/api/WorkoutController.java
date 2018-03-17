package com.workout.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@RestController
@RequestMapping("/workout")
@CrossOrigin
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WorkoutController {

    @Autowired
    private WorkoutService workoutService;


    @PostMapping
    public ResponseEntity<WorkOut> addWorkout(@RequestBody WorkOut workout) {
        return new ResponseEntity(workoutService.defineWorkout(workout), HttpStatus.OK);
    }

    @GetMapping(value = "{userId}")
    public ResponseEntity<List<WorkOut>> getWorkoutByUserID(@PathVariable Long userId) {
        return new ResponseEntity(workoutService.getWorkoutDetails(userId), HttpStatus.OK);
    }

}
