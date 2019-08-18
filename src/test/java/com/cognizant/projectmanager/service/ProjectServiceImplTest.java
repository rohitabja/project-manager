package com.cognizant.projectmanager.service;

import com.cognizant.projectmanager.dao.ProjectDao;
import com.cognizant.projectmanager.model.Project;
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
 * Created by hp on 18-08-2019.
 */
@RunWith(MockitoJUnitRunner.class)
public class ProjectServiceImplTest {

    @Mock
    private ProjectDao projectDao;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    public void testSaveProjectWhenProjectIdIsNull() throws Exception {
        final Project project = Project.builder().build();
        projectService.saveProject(project);
        verify(projectDao).addProject(project);
    }

    @Test
    public void testSaveProjectWhenProjectIdIsNotNull() throws Exception {
        final Project project = Project.builder()
                .projectId(1)
                .build();
        projectService.saveProject(project);
        verify(projectDao).updateProject(project);
    }

    @Test
    public void testGetProject() throws Exception {
        Project project = Project.builder().build();
        when(projectDao.getProject(1)).thenReturn(project);
        assertNotNull(projectService.getProject(1));
    }

    @Test
    public void testGetProjects() throws Exception {
        when(projectDao.getProjects()).thenReturn(Lists.newArrayList(
                Project.builder().build(),
                Project.builder().build()
        ));

        assertNotNull(projectService.getProjects());
        assertEquals(2, projectService.getProjects().size());
    }

}