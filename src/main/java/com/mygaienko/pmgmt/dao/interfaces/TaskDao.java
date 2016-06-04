package com.mygaienko.pmgmt.dao.interfaces;

import java.util.List;

import com.mygaienko.pmgmt.model.Project;
import com.mygaienko.pmgmt.model.Task;

public interface TaskDao {
	
	Task getById(int id);
	
	void delete(Task task);
	
	void persist(Task task);

	void refresh(Task task);

	List<Task> getAll();

	void merge(Task task);

	List<Task> getByProject(Project selectedProject);
}
