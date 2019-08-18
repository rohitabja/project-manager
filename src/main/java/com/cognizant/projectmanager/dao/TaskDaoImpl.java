package com.cognizant.projectmanager.dao;

import com.cognizant.projectmanager.model.ParentTask;
import com.cognizant.projectmanager.model.Project;
import com.cognizant.projectmanager.model.Task;
import com.cognizant.projectmanager.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.cognizant.projectmanager.util.AppConstants.*;
import static com.cognizant.projectmanager.util.AppUtil.*;

@Repository
@Slf4j
public class TaskDaoImpl implements TaskDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TaskDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    @Transactional
    public Task addTask(final Task request) {

        if (request.isParent()) {
            final KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update(connection -> {
                PreparedStatement ps = connection.prepareStatement("insert into parent_task (nm_parent_task) values (?)", Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, request.getTaskName().trim());
                return ps;
            }, keyHolder);
        }

        final KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "insert into task (nm_task, id_parent_task, id_project, id_user, priority, dt_start, dt_end, is_completed, dt_updated) " +
                            "values (?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, request.getTaskName().trim());
            ps.setObject(2, getParentTaskId(request.getParentTask()), Types.INTEGER);
            ps.setInt(3, request.getProject().getProjectId());
            ps.setInt(4, request.getUser().getUserId());
            ps.setObject(5, request.getPriority(), Types.INTEGER);
            ps.setDate(6, getSqlDate(request.getStartDate()));
            ps.setDate(7, getSqlDate(request.getEndDate()));
            ps.setString(8, "N");
            ps.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            return ps;
        }, keyHolder);

        final Task createdTask = Task.builder()
                .taskId(keyHolder.getKey().intValue())
                .taskName(request.getTaskName())
                .parent(request.isParent())
                .parentTask(request.getParentTask())
                .priority(request.getPriority())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .completed(request.isCompleted())
                .user(request.getUser())
                .project(request.getProject())
                .build();

        log.info("New task created in db {}", createdTask);

        return createdTask;
    }

    @Override
    public Task updateTask(final Task request) {
        log.info("Updating task {}", request);

        jdbcTemplate.update("update task set " +
                        " nm_task=?, id_parent_task=?, id_project=?, id_user=?, priority=?, dt_start=?, dt_end=?, is_completed=?, dt_updated=? " +
                        " where id_task=?",
                request.getTaskName(),
                getParentTaskId(request.getParentTask()),
                request.getProject().getProjectId(),
                request.getUser().getUserId(),
                request.getPriority(),
                getSqlDate(request.getStartDate()),
                getSqlDate(request.getEndDate()),
                getStrFromBoolean(request.isCompleted()),
                Timestamp.valueOf(LocalDateTime.now()),
                request.getTaskId());

        return request;
    }

    @Override
    public Task getTaskByTaskId(final Integer taskId) {
        return jdbcTemplate.queryForObject("select t.id_task, t.nm_task, t.id_parent_task, pt.nm_parent_task, " +
                        " t.id_project, p.nm_project, t.id_user, u.nm_first, u.nm_last, u.id_employee," +
                        " t.priority, t.dt_start, t.dt_end, t.is_completed, " +
                        " case when pt_1.id_parent_task is not null then 0" +
                        "   else 1" +
                        " end as parent " +
                        " from task t " +
                        " left outer join parent_task pt " +
                        "   on pt.id_parent_task = t.id_parent_task " +
                        " inner join project p " +
                        "   on p.id_project = t.id_project " +
                        " inner join user u " +
                        "   on u.id_user = t.id_user" +
                        " left outer join parent_task pt_1 " +
                        "   on pt_1.id_parent_task = t.id_task" +
                        " where id_task = ?",
                (resultSet, i) ->
                        Task.builder()
                                .taskId(resultSet.getInt(ID_TASK))
                                .taskName(resultSet.getString(NM_TASK))
                                .parentTask(buildParentTask(resultSet))
                                .project(Project.builder()
                                        .projectId(resultSet.getInt(ID_PROJECT))
                                        .projectName(resultSet.getString(NM_PROJECT))
                                        .build())
                                .user(User.builder()
                                        .userId(resultSet.getInt(ID_USER))
                                        .firstName(resultSet.getString(NM_FIRST))
                                        .lastName(resultSet.getString(NM_LAST))
                                        .employeeId(resultSet.getInt("id_employee"))
                                        .build())
                                .priority(getOptionalValue(resultSet, PRIORITY, Integer.class))
                                .startDate(getLocalDate(resultSet.getDate(DT_START)))
                                .endDate(getLocalDate(resultSet.getDate(DT_END)))
                                .completed(getBoolean(resultSet.getString(IS_COMPLETED)))
                                .parent(resultSet.getInt(PARENT) == 0)
                                .build(), taskId);
    }

    private ParentTask buildParentTask(ResultSet resultSet) throws SQLException {
        final Integer parentTaskId = getOptionalValue(resultSet, ID_PARENT_TASK, Integer.class);
        if (parentTaskId != null) {
            return ParentTask.builder()
                    .parentTaskId(parentTaskId)
                    .parentTaskName(resultSet.getString(NM_PARENT_TASK))
                    .build();
        }

        return null;
    }

    @Override
    public List<Task> getTasksByProjectId(final Integer projectId) {
        return jdbcTemplate.query(
                "select t.id_task, t.nm_task, pt.id_parent_task, pt.nm_parent_task, t.priority, " +
                        "t.dt_start, t.dt_end, t.is_completed, p.id_project, p.nm_project, u.id_user, u.nm_first, u.nm_last, " +
                        "case when pt_1.id_parent_task is not null then 0 " +
                        "     else 1 " +
                        "end as parent " +
                        "from task t " +
                        "join project p " +
                        "   on p.id_project = t.id_project " +
                        "join user u " +
                        "   on u.id_user = t.id_user " +
                        "left outer join parent_task pt " +
                        "   on pt.id_parent_task = t.id_parent_task " +
                        "left outer join parent_task pt_1 " +
                        "   on pt_1.id_parent_task = t.id_task " +
                        "where p.id_project = ?",
                (resultSet, i) -> Task.builder()
                        .taskId(resultSet.getInt(ID_TASK))
                        .taskName(resultSet.getString(NM_TASK))
                        .parentTask(buildParentTask(resultSet))
                        .priority(getOptionalValue(resultSet, PRIORITY, Integer.class))
                        .startDate(getLocalDate(resultSet.getDate(DT_START)))
                        .endDate(getLocalDate(resultSet.getDate(DT_END)))
                        .completed(getBoolean(resultSet.getString(IS_COMPLETED)))
                        .project(Project.builder()
                                .projectId(resultSet.getInt(ID_PROJECT))
                                .projectName(resultSet.getString(NM_PROJECT))
                                .build())
                        .user(User.builder()
                                .userId(resultSet.getInt(ID_USER))
                                .firstName(resultSet.getString(NM_FIRST))
                                .lastName(resultSet.getString(NM_LAST))
                                .build())
                        .parent(resultSet.getInt(PARENT) == 0)
                        .build(),
                projectId);
    }

    private Integer getParentTaskId(final ParentTask parentTask) {
        return parentTask == null ? null : parentTask.getParentTaskId();
    }

    @Override
    public List<ParentTask> getParentTasks() {
        return jdbcTemplate.query(
                "select * from parent_task",
                (resultSet, i) -> ParentTask.builder()
                        .parentTaskId(resultSet.getInt(ID_PARENT_TASK))
                        .parentTaskName(resultSet.getString(NM_PARENT_TASK))
                        .build());
    }

}
