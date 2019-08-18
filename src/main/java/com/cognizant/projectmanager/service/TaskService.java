package com.cognizant.projectmanager.service;

import com.cognizant.projectmanager.model.ParentTask;
import com.cognizant.projectmanager.model.Task;

import java.util.List;

public interface TaskService {

    Task getTaskByTaskId(final Integer taskId);
    List<Task> getTasksByProjectId(final Integer projectId);
    Task saveTask(final Task task);
    List<ParentTask> getParentTasks();
}
