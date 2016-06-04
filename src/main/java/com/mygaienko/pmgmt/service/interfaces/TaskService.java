package com.mygaienko.pmgmt.service.interfaces;

import java.util.List;

import com.mygaienko.pmgmt.model.AttachedFile;
import com.mygaienko.pmgmt.model.Project;
import com.mygaienko.pmgmt.model.Task;

public interface TaskService {
	
	Task getTaskById(int id);
	
	void deleteTask(Task task);
	
	void persist(Task task);

	void merge(Task task);

	void refresh(Task task);

	List<Task> getAllTasks();

	List<Task> getByProject(Project selectedProject);
}
