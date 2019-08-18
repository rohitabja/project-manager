package com.cognizant.projectmanager.dao;

import com.cognizant.projectmanager.model.Project;
import com.cognizant.projectmanager.model.Task;
import com.cognizant.projectmanager.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static com.cognizant.projectmanager.util.AppConstants.*;
import static com.cognizant.projectmanager.util.AppUtil.*;
import static java.lang.Math.toIntExact;
import static java.util.Collections.emptyList;
import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsLast;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.isEmpty;

/**
 * Created by hp on 10-08-2019.
 */
@Repository
@Slf4j
public class ProjectDaoImpl implements ProjectDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProjectDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Project getProject(final Integer projectId) {
        return jdbcTemplate.queryForObject("select p.id_project, p.nm_project, p.dt_start, p.dt_end, p.priority, " +
                        "u.id_user, u.nm_first, u.nm_last, u.id_employee " +
                        "from project p " +
                        "join user u " +
                        "   on u.id_user = p.id_user " +
                        "where id_project = ?",
                (resultSet, i) ->
                        Project.builder()
                                .projectId(resultSet.getInt(ID_PROJECT))
                                .projectName(resultSet.getString(NM_PROJECT))
                                .startDate(getLocalDate(resultSet.getDate(DT_START)))
                                .endDate(getLocalDate(resultSet.getDate(DT_END)))
                                .priority(getOptionalValue(resultSet, PRIORITY, Integer.class))
                                .manager(User.builder()
                                        .userId(resultSet.getInt(ID_USER))
                                        .firstName(resultSet.getString(NM_FIRST))
                                        .lastName(resultSet.getString(NM_LAST))
                                        .employeeId(resultSet.getInt(ID_EMPLOYEE))
                                        .build())
                                .build()
                , projectId);
    }

    @Override
    public List<Project> getProjects() {
        return jdbcTemplate.query(
                "select p.id_project, p.nm_project, p.dt_start, p.dt_end, p.priority, p.dt_updated, " +
                        "t.id_task, t.nm_task, t.is_completed " +
                        "from project p " +
                        "left outer join task t " +
                        "   on t.id_project = p.id_project " +
                        "order by p.dt_updated desc",
                (resultSet, i) ->
                        Task.builder()
                                .taskId(resultSet.getInt(ID_TASK))
                                .taskName(resultSet.getString(NM_TASK))
                                .completed(getBoolean(resultSet.getString(IS_COMPLETED)))
                                .project(Project.builder()
                                        .projectId(resultSet.getInt(ID_PROJECT))
                                        .projectName(resultSet.getString(NM_PROJECT))
                                        .startDate(getLocalDate(resultSet.getDate(DT_START)))
                                        .endDate(getLocalDate(resultSet.getDate(DT_END)))
                                        .priority(getOptionalValue(resultSet, PRIORITY, Integer.class))
                                        .updatedDate(getLocalDateTime(resultSet.getTimestamp(DT_UPDATED)))
                                        .build())
                                .build())
                .stream()
                .collect(groupingBy(t -> t.getProject()))
                .entrySet()
                .stream()
                .map(entry -> Project.builder()
                        .projectId(entry.getKey().getProjectId())
                        .projectName(entry.getKey().getProjectName())
                        .startDate(entry.getKey().getStartDate())
                        .endDate(entry.getKey().getEndDate())
                        .priority(entry.getKey().getPriority())
                        .updatedDate(entry.getKey().getUpdatedDate())
                        .tasks(isEmptyTask(entry.getValue()) ? emptyList() : entry.getValue())
                        .noOfTasksCompleted(noOfTaskCompleted(entry.getValue()))
                        .build())
                .sorted(nullsLast(comparing(Project::getUpdatedDate)).reversed())
                .collect(toList());
    }

    private Integer noOfTaskCompleted(final List<Task> tasks) {
        if(isEmptyTask(tasks)) {
            return 0;
        }

        return toIntExact(tasks.stream().filter(c -> c.isCompleted()).count());
    }

    private boolean isEmptyTask(final List<Task> tasks) {
        return isEmpty(tasks)
                || (tasks.size() == 1
                && (tasks.get(0).getTaskId() == null || tasks.get(0).getTaskId().intValue() == 0));
    }

    @Override
    public Project addProject(final Project request) {
        final KeyHolder keyHolder = new GeneratedKeyHolder();

        log.info("Saving new Project {}", request);
        jdbcTemplate.update(connection -> {
                    PreparedStatement ps = connection.prepareStatement(
                            "insert into project (nm_project, dt_start, dt_end, priority, id_user, dt_updated) " +
                                    "values (?, ?, ?, ?, ? ,?)", Statement.RETURN_GENERATED_KEYS);
                    ps.setString(1, request.getProjectName().trim());
                    ps.setDate(2, getSqlDate(request.getStartDate()));
                    ps.setDate(3, getSqlDate(request.getEndDate()));
                    ps.setObject(4, request.getPriority(), Types.INTEGER);
                    ps.setInt(5, request.getManager().getUserId());
                    ps.setTimestamp(6, Timestamp.valueOf(LocalDateTime.now()));
                    return ps;
                },
                keyHolder);

        final Project createdProject = Project.builder()
                .projectId(keyHolder.getKey().intValue())
                .projectName(request.getProjectName())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .priority(request.getPriority())
                .manager(request.getManager())
                .build();

        return createdProject;
    }

    @Override
    public Project updateProject(final Project request) {
        jdbcTemplate.update("update project set " +
                        "nm_project=?, dt_start=?, dt_end=?, priority=?, id_user=?, dt_updated=? " +
                        " where id_project=?",
                request.getProjectName(),
                getSqlDate(request.getStartDate()),
                getSqlDate(request.getEndDate()),
                request.getPriority(),
                request.getManager().getUserId(),
                Timestamp.valueOf(LocalDateTime.now()),
                request.getProjectId());

        return request;
    }

}
