package com.mygaienko.pmgmt.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.mygaienko.pmgmt.dao.interfaces.TaskDao;
import com.mygaienko.pmgmt.model.Project;
import com.mygaienko.pmgmt.model.Task;
import com.mygaienko.pmgmt.utils.EntityManagerSingleton;

public class TaskDaoImpl implements TaskDao {
	
	private EntityManager eM = EntityManagerSingleton.getEntityManager();
	
	@Override
	public Task getById(int id) {
		Task task = eM.find(Task.class, id);
		return task;
	}

	@Override
	public void delete(Task task) {
		task = eM.find(Task.class, task.getId());
		if (task != null) {
			eM.remove(task);
		}
	}
	
	@Override
	public void persist(Task task) {
		eM.getTransaction().begin();
		eM.persist(task);
		eM.getTransaction().commit();
	}

	@Override
	public void merge(Task task) {
		eM.getTransaction().begin();
		eM.merge(task);
		eM.getTransaction().commit();
	}

	@Override
	public void refresh(Task task) {
		eM.refresh(task);
	}
	
	@Override
	public List<Task> getAll() {
		@SuppressWarnings("unchecked")
		List<Task> tasks = (List<Task>) eM.createQuery("SELECT t FROM Task t")
				.getResultList();	            
		return tasks;
	}

	@Override
	public List<Task> getByProject(Project project) {
		@SuppressWarnings("unchecked")
		List<Task> tasks = (List<Task>) eM.createQuery("SELECT t FROM Task t WHERE t.project like :project")
				.setParameter("project", project)
				.getResultList();
		return tasks;
	}

}
