package com.theironyard;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by doug on 5/17/16.
 */
public interface WorkoutRepository extends CrudRepository<Workout, Integer> {

    List<Workout> findByExercise(String exercise);

}
