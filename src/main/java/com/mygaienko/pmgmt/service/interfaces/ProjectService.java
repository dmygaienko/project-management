package com.mygaienko.pmgmt.service.interfaces;

import java.util.List;

import com.mygaienko.pmgmt.model.Project;
import com.mygaienko.pmgmt.model.Task;

public interface ProjectService {
	
	void persist(Project project);
	
	void deleteProject(Project project);

	Project getProjectById(int id);

	List<Project> getAllProject();
	
	void merge(Project project);
}
