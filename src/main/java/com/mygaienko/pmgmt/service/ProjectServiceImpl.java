package com.mygaienko.pmgmt.service;

import java.util.List;

import com.mygaienko.pmgmt.dao.ProjectDaoImpl;
import com.mygaienko.pmgmt.dao.interfaces.ProjectDao;
import com.mygaienko.pmgmt.model.Project;
import com.mygaienko.pmgmt.service.interfaces.ProjectService;

public class ProjectServiceImpl implements ProjectService {
	private static ProjectServiceImpl instance;
	private ProjectDao pDao = new ProjectDaoImpl();
	
	private ProjectServiceImpl() {}
	
	public static ProjectServiceImpl getInstance() {
		if (instance != null) {
		} else {
			instance = new ProjectServiceImpl();
		}
		return instance;
	}
	
	@Override
	public void deleteProject(Project project) {
		pDao.deleteProject(project);
	}

	@Override
	public void persist(Project project) {
		pDao.persist(project);
	}
	
	@Override
	public void merge(Project project) {
		pDao.merge(project);
	}

	@Override
	public Project getProjectById(int id) {
		return pDao.getProjectById(id);
	}
	
	@Override
	public List<Project> getAllProject() {
		return pDao.getAllProject();
	}

}
