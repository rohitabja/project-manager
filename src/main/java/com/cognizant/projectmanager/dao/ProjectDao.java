package com.cognizant.projectmanager.dao;

import com.cognizant.projectmanager.model.Project;

import java.util.List;

/**
 * Created by hp on 10-08-2019.
 */
public interface ProjectDao {

    Project getProject(final Integer projectId);

    List<Project> getProjects();

    Project addProject(final Project project);

    Project updateProject(final Project project);
}
