package com.cognizant.projectmanager.service;

import com.cognizant.projectmanager.dao.ProjectDao;
import com.cognizant.projectmanager.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by hp on 10-08-2019.
 */
@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectDao projectDao;

    @Autowired
    public ProjectServiceImpl(final ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public Project saveProject(final Project project) {

        if(project.getProjectId() == null) {
            return projectDao.addProject(project);
        }

        return projectDao.updateProject(project);
    }

    @Override
    public Project getProject(final Integer projectId) {
        return projectDao.getProject(projectId);
    }

    @Override
    public List<Project> getProjects() {
        return projectDao.getProjects();
    }
}
