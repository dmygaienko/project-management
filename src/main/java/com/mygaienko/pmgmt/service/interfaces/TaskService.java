package com.mygaienko.pmgmt.service.interfaces;

import java.util.List;

import com.mygaienko.pmgmt.model.Task;

public interface TaskService {
	
	Task getTaskById(int id);
	
	void deleteTask(Task task);
	
	void persist(Task task);

	List<Task> getAllTasks();
}
