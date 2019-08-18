package com.cognizant.projectmanager.service;

import com.cognizant.projectmanager.model.Project;

import java.util.List;

/**
 * Created by hp on 10-08-2019.
 */
public interface ProjectService {

    Project saveProject(final Project project);
    Project getProject(final Integer projectId);
    List<Project> getProjects();
}
