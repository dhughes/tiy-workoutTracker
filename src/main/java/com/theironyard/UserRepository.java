package com.theironyard;

import org.springframework.data.repository.CrudRepository;

/**
 * Created by doug on 5/19/16.
 */
public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

}
