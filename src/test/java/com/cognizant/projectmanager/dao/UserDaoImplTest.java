package com.cognizant.projectmanager.dao;

import com.cognizant.projectmanager.TestDataSourceConfig;
import com.cognizant.projectmanager.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by hp on 18-08-2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UserDaoImpl.class})
@Import(TestDataSourceConfig.class)
public class UserDaoImplTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private JdbcTemplate template;

    @Test
    public void testGetUsers() throws Exception {
        final List<User> users = userDao.getUsers();
        assertFalse(users.isEmpty());
    }

    @Test
    public void testGetUser() throws Exception {
        final User user = userDao.getUser(2);
        assertNotNull(user);
    }

    @Test
    public void testUpdateUser() throws Exception {
        assertThat(1, is(userDao.getUser(1).getEmployeeId()));
        final User user = User.builder()
                .userId(1)
                .firstName("Test-1")
                .lastName("Test-2")
                .employeeId(567890)
                .build();

        userDao.updateUser(user);

        assertThat(567890, is(userDao.getUser(1).getEmployeeId()));
    }

    @Test
    public void testDeleteUser() throws Exception {
        assertFalse(userDao.getUser(1).isDeleted());

        userDao.deleteUser(1);

        assertTrue(userDao.getUser(1).isDeleted());
    }

    @Test
    public void testAddUser() {
        User user = User.builder()
                .firstName("Adrian")
                .lastName("Lopez")
                .employeeId(234578)
                .deleted(false)
                .build();
        User createdUser = userDao.addUser(user);
        assertNotNull(createdUser);
        assertNotNull(createdUser.getUserId());

        cleanup(createdUser.getUserId());
    }

    private void cleanup(final Integer userId) {
        template.update("delete from user where id_user = ?", userId);
    }


}