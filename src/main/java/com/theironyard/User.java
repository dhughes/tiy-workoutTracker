package com.theironyard;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by doug on 5/19/16.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private int id;

    private String username;

    private String password;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Workout> workouts;

    public User(){}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(Set<Workout> workouts) {
        this.workouts = workouts;
    }
}
