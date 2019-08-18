package com.cognizant.projectmanager.service;

import com.cognizant.projectmanager.dao.UserDao;
import com.cognizant.projectmanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hp on 10-08-2019.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(final UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User saveUser(final User user) {
        if(user.getUserId() == null) {
            return userDao.addUser(user);
        }

        return userDao.updateUser(user);
    }

    @Override
    public int deleteUser(Integer userId) {
        return userDao.deleteUser(userId);
    }

    @Override
    public User getUser(final Integer userId) {
        return userDao.getUser(userId);
    }

    @Override
    public List<User> getUsers() {
        return userDao.getUsers();
    }
}
