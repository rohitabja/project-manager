package com.cognizant.projectmanager.service;

import com.cognizant.projectmanager.dao.TaskDao;
import com.cognizant.projectmanager.model.ParentTask;
import com.cognizant.projectmanager.model.Task;
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
 * Created by hp on 26-06-2019.
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskServiceImplTest {

    @Mock
    private TaskDao taskDao;

    @InjectMocks
    private TaskServiceImpl taskService;

    @Test
    public void testGetTaskById() {
        Task task = Task.builder().build();
        when(taskDao.getTaskByTaskId(1)).thenReturn(task);
        assertEquals(task, taskService.getTaskByTaskId(1));
    }

    @Test
    public void testSaveTask() {
        Task task = Task.builder().build();
        taskService.saveTask(task);
        verify(taskDao).addTask(task);
    }

    @Test
    public void testUpdateTask() {
        Task task = Task.builder().taskId(1).build();
        taskService.saveTask(task);
        verify(taskDao).updateTask(task);
    }

    @Test
    public void testGetTaskByProjectId() {
        when(taskDao.getTasksByProjectId(1)).thenReturn(Lists.newArrayList(
                Task.builder().build(),
                Task.builder().build(),
                Task.builder().build()
        ));
        assertNotNull(taskService.getTasksByProjectId(1));
        assertEquals(3, taskService.getTasksByProjectId(1).size());
    }

    @Test
    public void testGetParentTasks() {
        when(taskDao.getParentTasks()).thenReturn(Lists.newArrayList(
                ParentTask.builder().build(),
                ParentTask.builder().build()
        ));
        assertNotNull(taskService.getParentTasks());
        assertEquals(2, taskService.getParentTasks().size());
    }

}