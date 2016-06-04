package com.mygaienko.pmgmt.dao.interfaces;

import java.util.List;

import com.mygaienko.pmgmt.model.Project;

public interface ProjectDao {
	
	void persist(Project project);
	
	void delete(Project project);

	Project getById(int id);

	List<Project> getAllProject();

	void merge(Project project);
}
