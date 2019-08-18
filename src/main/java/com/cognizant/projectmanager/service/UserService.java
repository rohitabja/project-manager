package com.cognizant.projectmanager.service;

import com.cognizant.projectmanager.model.User;

import java.util.List;

/**
 * Created by hp on 10-08-2019.
 */
public interface UserService {

    User saveUser(final User user);

    int deleteUser(final Integer userId);

    User getUser(final Integer userId);

    List<User> getUsers();
}
