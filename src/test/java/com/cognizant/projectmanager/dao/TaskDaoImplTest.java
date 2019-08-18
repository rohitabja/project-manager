package com.cognizant.projectmanager.dao;

import com.cognizant.projectmanager.TestDataSourceConfig;
import com.cognizant.projectmanager.model.Project;
import com.cognizant.projectmanager.model.Task;
import com.cognizant.projectmanager.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by hp on 18-08-2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TaskDaoImpl.class})
@Import(TestDataSourceConfig.class)
public class TaskDaoImplTest {

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private JdbcTemplate template;

    @Test
    public void testAddTask() throws Exception {
        final Task task = Task.builder()
                .taskName("test-task")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .priority(10)
                .user(User.builder()
                        .userId(1)
                        .build())
                .project(Project.builder()
                        .projectId(2)
                        .build())
                .parent(true)
                .build();

        final Task createdTask = taskDao.addTask(task);

        assertNotNull(createdTask.getTaskId());

        cleanupTask(createdTask.getTaskId());
    }

    @Test
    public void testUpdateTask() throws Exception {
        final Task task = Task.builder()
                .taskName("test-task")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .priority(10)
                .user(User.builder()
                        .userId(1)
                        .build())
                .project(Project.builder()
                        .projectId(2)
                        .build())
                .build();

        final Task createdTask = taskDao.addTask(task);
        assertNotNull(createdTask.getTaskId());

        final Task updatedTask = Task.builder()
                .taskId(createdTask.getTaskId())
                .taskName("test-task-updated")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .priority(10)
                .user(User.builder()
                        .userId(1)
                        .build())
                .project(Project.builder()
                        .projectId(1)
                        .build())
                .build();

        taskDao.updateTask(updatedTask);

        assertThat("test-task-updated", is(taskDao.getTaskByTaskId(updatedTask.getTaskId()).getTaskName()));

        cleanupTask(updatedTask.getTaskId());
    }

    @Test
    public void testGetTaskByTaskId() throws Exception {

        final Task task = Task.builder()
                .taskName("test-task-by-taskid")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .priority(10)
                .user(User.builder()
                        .userId(1)
                        .build())
                .project(Project.builder()
                        .projectId(1)
                        .build())
                .build();

        final Task createdTask = taskDao.addTask(task);

        assertThat("test-task-by-taskid",
                is(taskDao.getTaskByTaskId(createdTask.getTaskId()).getTaskName()));

        cleanupTask(createdTask.getTaskId());
    }

    @Test
    public void testGetTasksByProjectId() throws Exception {

        final Task task = Task.builder()
                .taskName("test-task-by-projectid")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .priority(10)
                .user(User.builder()
                        .userId(1)
                        .build())
                .project(Project.builder()
                        .projectId(1)
                        .build())
                .build();

        final Task createdTask = taskDao.addTask(task);

        assertThat(1,
                is(taskDao.getTasksByProjectId(1).size()));

        cleanupTask(createdTask.getTaskId());

    }

    @Test
    public void testGetParentTasks() throws Exception {
        assertTrue(taskDao.getParentTasks().size() > 0);
    }

    private void cleanupTask(final Integer taskId) {
        template.update("delete from task where id_task = ?", taskId);
    }

    private void cleanupParentTask(final Integer parentTaskId) {
        template.update("delete from parent_task where id_parent_task = ?", parentTaskId);
    }

}