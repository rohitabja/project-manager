package com.cognizant.projectmanager.controller;

import com.cognizant.projectmanager.model.Project;
import com.cognizant.projectmanager.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Created by hp on 10-08-2019.
 */
@RestController
@RequestMapping(value = "api/project", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
@Slf4j
@CrossOrigin
public class ProjectController {

    private final ProjectService projectService;

    @Autowired
    public ProjectController(final ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public Project saveProject(@RequestBody @Valid final Project project) {
        return projectService.saveProject(project);
    }

    @GetMapping("{projectId}")
    public Project getProject(@PathVariable("projectId") final Integer projectId) {
        return projectService.getProject(projectId);
    }

    @GetMapping
    public List<Project> getProjects() {
        return projectService.getProjects();
    }
}
