package com.cognizant.projectmanager.controller;

import com.cognizant.projectmanager.model.ParentTask;
import com.cognizant.projectmanager.model.Task;
import com.cognizant.projectmanager.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "api/task", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@Slf4j
@CrossOrigin
public class TaskController {

    private final TaskService taskService;

    @Autowired
    public TaskController(final TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("{taskId}")
    public Task getTaskByTaskId(@PathVariable("taskId") final Integer taskId) {
        log.info("Retrieving task for taskId {}", taskId);
        return taskService.getTaskByTaskId(taskId);
    }

    @GetMapping("project/{projectId}")
    public List<Task> getTasksByProjectId(@PathVariable("projectId") final Integer projectId) {
        log.info("Retrieving tasks for project Id {}", projectId);
        return taskService.getTasksByProjectId(projectId);
    }

    @GetMapping(value = "parent", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    public List<ParentTask> getParentTasks() {
        log.info("Retrieving parent tasks");
        return taskService.getParentTasks();
    }

    @PostMapping
    public Task saveTask(@RequestBody @Valid final Task task) {
        log.info("Saving task {}", task);
        return taskService.saveTask(task);
    }
}
