package com.cognizant.projectmanager.dao;

import com.cognizant.projectmanager.exception.TaskManagerException;
import com.cognizant.projectmanager.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

import static com.cognizant.projectmanager.util.AppConstants.*;
import static com.cognizant.projectmanager.util.AppUtil.getBoolean;
import static com.cognizant.projectmanager.util.AppUtil.getStrFromBoolean;

/**
 * Created by hp on 10-08-2019.
 */
@Repository
@Slf4j
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<User> getUsers() {
        return jdbcTemplate.query(
                "select * from user order by dt_updated desc",
                (resultSet, i) -> User.builder()
                        .userId(resultSet.getInt(ID_USER))
                        .employeeId(resultSet.getInt(ID_EMPLOYEE))
                        .firstName(resultSet.getString(NM_FIRST))
                        .lastName(resultSet.getString(NM_LAST))
                        .deleted(getBoolean(resultSet.getString(DELETED)))
                        .build());
    }

    @Override
    public User getUser(final Integer userId) {
        try {
            return jdbcTemplate.queryForObject("select * from user where id_user = ?",
                    (resultSet, i) -> User.builder()
                            .userId(resultSet.getInt(ID_USER))
                            .employeeId(resultSet.getInt(ID_EMPLOYEE))
                            .firstName(resultSet.getString(NM_FIRST))
                            .lastName(resultSet.getString(NM_LAST))
                            .deleted(getBoolean(resultSet.getString(DELETED)))
                            .build(),
                    userId);
        } catch (final Exception ex) {
            throw new TaskManagerException("User id " + userId + " not found", ex);
        }
    }

    @Override
    public User addUser(final User request) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        log.info("Saving new user {}", request);
        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            "insert into user (nm_first, nm_last, id_employee, deleted, dt_updated) " +
                                    "values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, request.getFirstName().trim());
                    ps.setString(2, request.getLastName().trim());
                    ps.setInt(3, request.getEmployeeId());
                    ps.setString(4, "N");
                    ps.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
                    return ps;
                }
                , keyHolder);

        final User createdUser = User.builder()
                .userId(keyHolder.getKey().intValue())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .employeeId(request.getEmployeeId())
                .build();

        return createdUser;
    }

    @Override
    public User updateUser(final User user) {
        jdbcTemplate.update("update user set " +
                        " nm_first=?, nm_last=?, id_employee=?, deleted=?, dt_updated=? " +
                        " where id_user=?",
                user.getFirstName(),
                user.getLastName(),
                user.getEmployeeId(),
                getStrFromBoolean(user.isDeleted()),
                Timestamp.valueOf(LocalDateTime.now()),
                user.getUserId());

        return user;
    }

    @Override
    public int deleteUser(final Integer userId) {
        int count = jdbcTemplate.update("update user set " +
                        " deleted=? " +
                        " where id_user=?",
                "Y", userId);

        return count;
    }
}
