package com.mygaienko.pmgmt.dao.interfaces;

import java.util.List;

import com.mygaienko.pmgmt.model.Task;

public interface TaskDao {
	
	Task getTaskById(int id);
	
	void deleteTask(Task task);
	
	void persist(Task task);

	void refresh(Task task);

	List<Task> getAllTasks();

	void merge(Task task);
}
