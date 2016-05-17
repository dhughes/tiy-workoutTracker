package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by doug on 5/17/16.
 */
@Controller
public class WorkoutTrackerController {

    @Autowired
    WorkoutRepository workoutRepository;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String workoutList(Model model, String search){

        Iterable<Workout> workouts;
        if(search == null) {
            // read it from the DB
            workouts = workoutRepository.findAll();
        } else {
            // find exercise by name:
            workouts = workoutRepository.findByExercise(search);
        }

        // add workouts into model
        model.addAttribute("workouts", workouts);

        // show the home template
        return "home";
    }

    @RequestMapping(path = "/add-workout", method = RequestMethod.POST)
    public String addWorkout(String exercise, Integer reps, Integer sets){

        Workout workout = new Workout();
        workout.setExercise(exercise);
        workout.setReps(reps);
        workout.setSets(sets);

        workoutRepository.save(workout);

        return "redirect:/";
    }

}
