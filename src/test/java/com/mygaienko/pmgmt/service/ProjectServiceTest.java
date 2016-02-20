package com.mygaienko.pmgmt.service;

import org.junit.Test;

import com.mygaienko.pmgmt.model.Project;
import com.mygaienko.pmgmt.model.Task;

public class ProjectServiceTest {
	private static final String TEST_T_NAME = "test_tName";
	private static final String TEST_P_NAME = "test_pName";
	private static final String TEST_P_DESCRIPTION = "test_pDescription";
	private  ProjectService service = ProjectServiceImpl.getInstance();
	
	@Test
	public void testPersistSimpleProject() {
		Project project = new Project();
		project.setDesription(TEST_P_DESCRIPTION);
		project.setName(TEST_P_NAME);
		service.persist(project);
	}
	
	@Test
	public void testPersistComplexProject() {
		Project project = new Project();
		project.setDesription(TEST_P_DESCRIPTION);
		project.setName(TEST_P_NAME);
		
		Task task = new Task();
		task.setName(TEST_T_NAME);
		
		project.addTask(task);
		
		service.persist(project);
	}
	
	
}
