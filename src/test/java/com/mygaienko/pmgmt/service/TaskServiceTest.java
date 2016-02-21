package com.mygaienko.pmgmt.service;

import com.mygaienko.pmgmt.service.interfaces.TaskService;
import org.junit.Test;

import com.mygaienko.pmgmt.model.Project;
import com.mygaienko.pmgmt.model.Task;

public class TaskServiceTest {
	private static final String TEST_P_NAME = "test_pName";
	private static final String TEST_P_DESCRIPTION = "test_pDescription";
	private static final String TEST_T_NAME = "test_tName";
	private TaskService service = TaskServiceImpl.getInstance();
	
	@Test
	public void testPersistSimpleTask() {
		Task task = new Task();
		task.setName(TEST_T_NAME);
		service.persist(task);
	}
	
	@Test
	public void testPersistComplexTask() {
		Task task = new Task();
		task.setName(TEST_T_NAME);
		
		Project project = new Project();
		project.setDesription(TEST_P_DESCRIPTION);
		project.setName(TEST_P_NAME);
		
		task.setProject(project);
		
		service.persist(task);
	}
}
