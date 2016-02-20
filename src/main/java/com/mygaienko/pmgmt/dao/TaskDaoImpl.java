package com.mygaienko.pmgmt.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.mygaienko.pmgmt.model.Executor;
import com.mygaienko.pmgmt.model.Task;
import com.mygaienko.pmgmt.utils.EntityManagerSingleton;

public class TaskDaoImpl implements TaskDao {
	
	private EntityManager eM = EntityManagerSingleton.getEntityManager();
	
	@Override
	public Task getTaskById(int id) {
		Task task = eM.find(Task.class, id);
		return task;
	}

	@Override
	public void deleteTask(Task task) {
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
	public List<Task> getAllTasks() {
		@SuppressWarnings("unchecked")
		List<Task> tasks = (List<Task>) eM.createQuery("SELECT t FROM Task t")
				.getResultList();	            
		return tasks;
	}

}
