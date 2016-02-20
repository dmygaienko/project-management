package com.mygaienko.pmgmt.dao;

import java.util.List;

import com.mygaienko.pmgmt.model.Executor;
import com.mygaienko.pmgmt.model.Task;

public interface TaskDao {
	
	Task getTaskById(int id);
	
	void deleteTask(Task task);
	
	void persist(Task task);

	List<Task> getAllTasks();
}
