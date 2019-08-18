package com.cognizant.projectmanager.service;

import com.cognizant.projectmanager.dao.TaskDao;
import com.cognizant.projectmanager.model.ParentTask;
import com.cognizant.projectmanager.model.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    private final TaskDao taskDao;

    @Autowired
    public TaskServiceImpl(final TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @Override
    public Task getTaskByTaskId(final Integer taskId) {
        final Task task = taskDao.getTaskByTaskId(taskId);
        return task;
    }

    @Override
    public List<Task> getTasksByProjectId(final Integer projectId) {
        return taskDao.getTasksByProjectId(projectId);
    }

    @Override
    public Task saveTask(final Task task) {

        if(task.getTaskId() == null) {
            log.info("Task id is null, adding task info {}", task);
            return taskDao.addTask(task);
        }

        log.info("Task id is not null, updating task info {}", task);
        return taskDao.updateTask(task);
    }

    @Override
    public List<ParentTask> getParentTasks() {
        return taskDao.getParentTasks();
    }

}
