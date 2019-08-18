package com.cognizant.projectmanager;

import com.cognizant.projectmanager.controller.TaskController;
import com.cognizant.projectmanager.dao.TaskDao;
import com.cognizant.projectmanager.service.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
		, classes = TaskManagerApplication.class)
public class TaskManagerApplicationTests {

	@Autowired
	private TaskController taskController;

	@Autowired
	private TaskService taskService;

	@Autowired
	private TaskDao taskDao;

	@Test
	public void contextLoads() {
		assertThat(taskController).isNotNull();
		assertThat(taskService).isNotNull();
		assertThat(taskDao).isNotNull();
	}

}
