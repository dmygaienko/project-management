package com.mygaienko.pmgmt.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.mygaienko.pmgmt.model.Executor;
import com.mygaienko.pmgmt.model.Project;
import com.mygaienko.pmgmt.model.Task;
import com.mygaienko.pmgmt.utils.EntityManagerSingleton;

public class ProjectDaoImpl implements ProjectDao {
	
	private EntityManager eM = EntityManagerSingleton.getEntityManager();
	
	@Override
	public void deleteProject(Project project) {
		eM.getTransaction().begin();
		project = eM.find(Project.class, project.getId());
		if (project != null) {
			eM.remove(project);
		}
		eM.getTransaction().commit();
	}

	@Override
	public void persist(Project project) {
		eM.getTransaction().begin();
		eM.persist(project);
		eM.getTransaction().commit();
	}
	
	@Override
	public void merge(Project project) {
		eM.getTransaction().begin();
		eM.merge(project);
		eM.getTransaction().commit();
	}

	@Override
	public Project getProjectById(int id) {
		Project project = eM.find(Project.class, id);
		return project;
	}

	@Override
	public List<Project> getAllProject() {
		@SuppressWarnings("unchecked")
		List<Project> projects = (List<Project>) eM.createQuery("SELECT p FROM Project p")
				.getResultList();	            
		return projects;
	}

}
