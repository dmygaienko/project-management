package com.mygaienko.pmgmt.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import com.mygaienko.pmgmt.service.interfaces.ExecutorService;
import org.junit.Test;

import com.mygaienko.pmgmt.model.Executor;
import com.mygaienko.pmgmt.model.Task;

public class ExecutorServiceTest {

	private static final String TEST_LASTNAME1 = "test_lastname1";
	private static final String TEST_FIRSTNAME1 = "test_firstname1";
	private static final String TEST_LASTNAME2 = "test_lastname2";
	private static final String TEST_FIRSTNAME2 = "test_firstname2";
	
	private ExecutorService service = ExecutorServiceImpl.getInstance();
	
	@Test
	public void testPersist() {
		Executor executor = new Executor();
		executor.setFirstName(TEST_FIRSTNAME1);
		executor.setLastName(TEST_LASTNAME1);
		service.persist(executor);
	}
	
	@Test
	public void testPersistTwiceInARow() {
		Executor executor = new Executor();
		executor.setFirstName(TEST_FIRSTNAME1);
		executor.setLastName(TEST_LASTNAME1);
		service.persist(executor);
		
		Executor executor1 = new Executor();
		executor1.setFirstName(TEST_FIRSTNAME2);
		executor1.setLastName(TEST_LASTNAME2);
		service.persist(executor1);
	}
	
	@Test
	public void testGetExecutorByLastName() {
		Executor executor = new Executor();
		executor.setFirstName(TEST_FIRSTNAME1);
		executor.setLastName(TEST_LASTNAME1);
		service.persist(executor);
		assertEquals(TEST_LASTNAME1, service.getExecutorByLastName(TEST_LASTNAME1).getLastName());
	}
	
	@Test
	public void testGetAllExecutors() {
		Executor executor = new Executor();
		executor.setFirstName(TEST_FIRSTNAME1);
		executor.setLastName(TEST_LASTNAME1);
		service.persist(executor);
		
		Executor executor1 = new Executor();
		executor1.setFirstName(TEST_FIRSTNAME2);
		executor1.setLastName(TEST_LASTNAME2);
		service.persist(executor1);
		System.out.println(service.getAllExecutors());
	}
	
	@Test
	public void testPersistWithTask() {
		Executor executor = new Executor();
		executor.setFirstName(TEST_FIRSTNAME1);
		executor.setLastName(TEST_LASTNAME1);
		
		Task task = new Task();
		task.setName("test_task");
		executor.addTask(task);
		
		service.persist(executor);
	}
}
