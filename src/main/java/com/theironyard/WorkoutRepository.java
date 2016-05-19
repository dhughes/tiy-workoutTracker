package com.theironyard;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by doug on 5/17/16.
 */
public interface WorkoutRepository extends CrudRepository<Workout, Integer> {

    List<Workout> findByMuscleGroupAndUser(String muscleGroup, User user);

    @Query("SELECT w FROM Workout w WHERE LOWER(w.exercise) LIKE LOWER(CONCAT('%', ?1, '%')) AND w.user = ?2")
    List<Workout> findByExerciseStartsWithAndUser(String name, User user);
}
