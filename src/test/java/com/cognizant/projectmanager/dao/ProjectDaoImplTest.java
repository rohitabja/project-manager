package com.cognizant.projectmanager.dao;

import com.cognizant.projectmanager.TestDataSourceConfig;
import com.cognizant.projectmanager.model.Project;
import com.cognizant.projectmanager.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * Created by hp on 18-08-2019.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProjectDaoImpl.class})
@Import(TestDataSourceConfig.class)
public class ProjectDaoImplTest {

    @Autowired
    private ProjectDao projectDao;
    @Autowired
    private JdbcTemplate template;

    @Test
    public void getProject() throws Exception {

        Project project = Project.builder()
                .projectName("Test-project")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .manager(User.builder()
                        .userId(1)
                        .build())
                .priority(10)
                .build();

        Project createdProject = projectDao.addProject(project);

        Project result = projectDao.getProject(createdProject.getProjectId());

        assertNotNull(result);

        cleanup(result.getProjectId());
    }

    @Test
    public void getProjects() throws Exception {
        Project project1 = Project.builder()
                .projectName("Test-project")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .manager(User.builder()
                        .userId(1)
                        .build())
                .priority(10)
                .build();

        Project project2 = Project.builder()
                .projectName("Test-project2")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .manager(User.builder()
                        .userId(1)
                        .build())
                .priority(10)
                .build();

        Project createdProject1 = projectDao.addProject(project1);
        Project createdProject2 = projectDao.addProject(project2);

        List<Project> projects = projectDao.getProjects();

        assertNotNull(projects);
        assertTrue(projects.size() > 0);

        cleanup(createdProject1.getProjectId());
        cleanup(createdProject2.getProjectId());
    }

    @Test
    public void addProject() throws Exception {
        Project project = Project.builder()
                .projectName("Test-project")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .manager(User.builder()
                        .userId(1)
                        .build())
                .priority(10)
                .build();

        Project createdProject = projectDao.addProject(project);
        assertNotNull(createdProject.getProjectId());

        cleanup(createdProject.getProjectId());
    }

    @Test
    public void updateProject() throws Exception {
        Project project = Project.builder()
                .projectName("Test-project")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .manager(User.builder()
                        .userId(1)
                        .build())
                .priority(10)
                .build();

        Project createdProject = projectDao.addProject(project);
        assertNotNull(createdProject.getProjectId());

        Project updatedProject = Project.builder()
                .projectId(createdProject.getProjectId())
                .projectName("Test-project-updated")
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .manager(User.builder()
                        .userId(1)
                        .build())
                .priority(10)
                .build();

        projectDao.updateProject(updatedProject);

        assertThat("Test-project-updated", is(projectDao.getProject(updatedProject.getProjectId()).getProjectName()));

        cleanup(updatedProject.getProjectId());
    }

    private void cleanup(final Integer projectId) {
        template.update("delete from project where id_project = ?", projectId);
    }

}