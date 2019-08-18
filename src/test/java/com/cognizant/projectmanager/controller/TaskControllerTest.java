package com.cognizant.projectmanager.controller;

import com.cognizant.projectmanager.model.ParentTask;
import com.cognizant.projectmanager.model.Task;
import com.cognizant.projectmanager.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by hp on 26-06-2019.
 */
@RunWith(MockitoJUnitRunner.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    @Test
    public void testGetTaskByTaskId() {
        when(taskService.getTaskByTaskId(1)).thenReturn(Task.builder().build());
        assertNotNull(taskController.getTaskByTaskId(1));
    }

    @Test
    public void testGetTasksByProjectId() {
        when(taskService.getTasksByProjectId(1)).thenReturn(newArrayList(
                Task.builder().build(),
                Task.builder().build()));
        assertNotNull(taskController.getTasksByProjectId(1));
        assertEquals(2, taskController.getTasksByProjectId(1).size());
    }

    @Test
    public void testGetParentTasks() {
        when(taskService.getParentTasks()).thenReturn(newArrayList(
                ParentTask.builder().build(),
                ParentTask.builder().build(),
                ParentTask.builder().build()));
        assertNotNull(taskController.getParentTasks());
        assertEquals(3, taskController.getParentTasks().size());
    }

    @Test
    public void testSaveTask() {
        Task task = Task.builder().build();
        taskController.saveTask(task);
        verify(taskService).saveTask(task);
    }

}