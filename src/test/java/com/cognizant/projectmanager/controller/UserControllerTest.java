package com.cognizant.projectmanager.controller;

import com.cognizant.projectmanager.model.User;
import com.cognizant.projectmanager.service.UserService;
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
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private static final User DUMMY_USER = User.builder().build();

    @Test
    public void testSaveUser() throws Exception {
        userController.saveUser(DUMMY_USER);
        verify(userService).saveUser(DUMMY_USER);
    }

    @Test
    public void testDeleteUser() throws Exception {
        userController.deleteUser(1);
        verify(userService).deleteUser(1);
    }

    @Test
    public void testGetUser() throws Exception {
        when(userService.getUser(1)).thenReturn(DUMMY_USER);
        assertNotNull(userController.getUser(1));
    }

    @Test
    public void testGetUsers() throws Exception {
        when(userService.getUsers()).thenReturn(Lists.newArrayList(DUMMY_USER,
                User.builder().build()));

        assertNotNull(userController.getUsers());
        assertEquals(2, userController.getUsers().size());
    }

}