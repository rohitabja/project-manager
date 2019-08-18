package com.cognizant.projectmanager.dao;

import com.cognizant.projectmanager.model.User;

import java.util.List;

/**
 * Created by hp on 10-08-2019.
 */
public interface UserDao {

    List<User> getUsers();

    User getUser(final Integer userId);

    User addUser(final User user);

    User updateUser(final User user);

    int deleteUser(final Integer userId);
}
