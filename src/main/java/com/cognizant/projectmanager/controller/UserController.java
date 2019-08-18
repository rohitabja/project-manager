package com.cognizant.projectmanager.controller;

import com.cognizant.projectmanager.model.User;
import com.cognizant.projectmanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by hp on 10-08-2019.
 */
@RestController
@RequestMapping(value = "api/user", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@Slf4j
@CrossOrigin
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User saveUser(@RequestBody @Valid final User user) {
        return userService.saveUser(user);
    }

    @DeleteMapping("{userId}")
    public void deleteUser(@PathVariable("userId") final Integer userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("{userId}")
    public User getUser(@PathVariable("userId") final Integer userId) {
        return userService.getUser(userId);
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getUsers();
    }
}
