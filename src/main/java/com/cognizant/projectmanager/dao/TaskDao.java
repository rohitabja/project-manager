package com.cognizant.projectmanager.dao;

import com.cognizant.projectmanager.model.ParentTask;
import com.cognizant.projectmanager.model.Task;

import java.util.List;

public interface TaskDao {

    Task addTask(final Task task);
    Task updateTask(final Task task);
    Task getTaskByTaskId(final Integer taskId);
    List<Task> getTasksByProjectId(final Integer projectId);
    List<ParentTask> getParentTasks();

}
