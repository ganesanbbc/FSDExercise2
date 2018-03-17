package com.workout.transaction;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/transaction")
@CrossOrigin
public class TransactionController {

    @Autowired
    private TransactionService service;

    @PostMapping
    public ResponseEntity<Transactions> computeDuration(@RequestBody Transactions transactions) {

        transactions.setDuration(Duration.between(transactions.getStartTime(), transactions.getStopTime()));
        transactions.setCalBurnt(calBurnt(transactions.getDuration(), transactions.getWorkout().getCalBurntPerUnitTime()));
        Transactions workoutTxns = service.updateWorkoutTxnDetails(transactions);
        return new ResponseEntity(workoutTxns, HttpStatus.OK);
    }

    @GetMapping(value = "{workoutId}")
    public ResponseEntity<List<Transactions>> getWorkoutDetails(@PathVariable Long workoutId) {
        List<Transactions> workoutTxnDetails = service.getWorkoutTxnDetails(workoutId);
        return new ResponseEntity(workoutTxnDetails, HttpStatus.OK);
    }

    private Double calBurnt(Duration duration, Double calBurntPerUnitTime) {
        return (calBurntPerUnitTime) * (NANOSECONDS.toSeconds((long) duration.toNanos()));
    }

}
