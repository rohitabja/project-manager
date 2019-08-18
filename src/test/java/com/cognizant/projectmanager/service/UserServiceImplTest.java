package com.cognizant.projectmanager.service;

import com.cognizant.projectmanager.dao.UserDao;
import com.cognizant.projectmanager.model.User;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by hp on 18-08-2019.
 */
@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void testSaveUserWhenUserIdIsNull() throws Exception {
        final User user = User.builder().build();

        userService.saveUser(user);

        verify(userDao).addUser(user);
    }

    @Test
    public void testSaveUserWhenUserIdIsNotNull() throws Exception {
        final User user = User.builder()
                .userId(1)
                .build();

        userService.saveUser(user);

        verify(userDao).updateUser(user);
    }

    @Test
    public void testDeleteUser() throws Exception {
        userService.deleteUser(1);

        verify(userDao).deleteUser(1);
    }

    @Test
    public void testGetUser() throws Exception {
        final User user = User.builder().build();
        when(userDao.getUser(1)).thenReturn(user);

        userService.getUser(1);

        assertNotNull(userService.getUser(1));
    }

    @Test
    public void getUsers() throws Exception {
        when(userDao.getUsers()).thenReturn(Lists.newArrayList(
                User.builder().build(),
                User.builder().build()
        ));

        assertNotNull(userService.getUsers());
        assertEquals(2, userService.getUsers().size());
    }

}