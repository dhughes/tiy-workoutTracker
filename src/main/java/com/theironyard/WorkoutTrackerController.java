package com.theironyard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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

    @Autowired
    UserRepository userRepository;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String workoutList(HttpSession session, Model model, String search, String muscleGroup){
        User user = userRepository.findByUsername((String)session.getAttribute("username"));

        if(user == null){
            return "redirect:/login";
        }

        Iterable<Workout> workouts;
        if(search != null) {
            workouts = workoutRepository.findByExerciseStartsWithAndUser(search, user);
        } else if(muscleGroup != null){
            workouts = workoutRepository.findByMuscleGroupAndUser(muscleGroup, user);
        } else {
            workouts = user.getWorkouts();
        }

        // add workouts into model
        model.addAttribute("workouts", workouts);

        // show the home template
        return "home";
    }

    @RequestMapping(path = "/login", method = RequestMethod.GET)
    public String loginForm(){

        return "login";
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String doLogin(HttpSession session, Model model, String username, String password){

        User user = userRepository.findByUsernameAndPassword(username, password);

        if(user != null){
            session.setAttribute("username", user.getUsername());
            return "redirect:/";
        }

        model.addAttribute("error", "Login Failed!");
        model.addAttribute("username", username);

        return "login";
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public String logout(HttpSession session){
        session.invalidate();

        return "redirect:/login";
    }

    @RequestMapping(path = "/add-workout", method = RequestMethod.POST)
    public String addWorkout(HttpSession session, String exercise, String muscleGroup, Integer reps, Integer sets, String notes){
        User user = userRepository.findByUsername((String)session.getAttribute("username"));

        if(user == null){
            return "redirect:/login";
        }

        Workout workout = new Workout();
        workout.setMuscleGroup(muscleGroup);
        workout.setExercise(exercise);
        workout.setReps(reps);
        workout.setSets(sets);
        workout.setNotes(notes);
        workout.setUser(user);

        workoutRepository.save(workout);

        return "redirect:/";
    }

}
