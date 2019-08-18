package com.cognizant.projectmanager.controller;

import com.cognizant.projectmanager.model.Project;
import com.cognizant.projectmanager.service.ProjectService;
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
public class ProjectControllerTest {

    @Mock
    private ProjectService projectService;

    @InjectMocks
    private ProjectController projectController;

    private static final Project DUMMY_PROJECT = Project.builder().build();

    @Test
    public void testSaveProject() throws Exception {
        projectController.saveProject(DUMMY_PROJECT);
        verify(projectService).saveProject(DUMMY_PROJECT);
    }

    @Test
    public void testGetProject() throws Exception {
        when(projectService.getProject(1)).thenReturn(DUMMY_PROJECT);
        assertNotNull(projectController.getProject(1));
    }

    @Test
    public void getProjects() throws Exception {
        when(projectService.getProjects()).thenReturn(Lists.newArrayList(DUMMY_PROJECT,
                Project.builder().build()));
        assertNotNull(projectController.getProjects());
        assertEquals(2, projectController.getProjects().size());
    }

}