package com.mygaienko.pmgmt.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.NotSupportedException;
import javax.transaction.SystemException;

import com.mygaienko.pmgmt.model.Executor;
import com.mygaienko.pmgmt.model.Task;
import com.mygaienko.pmgmt.utils.EntityManagerSingleton;

public class ExecutorDaoImpl implements ExecutorDao {
	
	private EntityManager eM = EntityManagerSingleton.getEntityManager();
	
	@Override
	public Executor getExecutorById(int id) {
		Executor executor = eM.find(Executor.class, id);
		return executor;
	}
	
	@Override
	public Executor getExecutorByLastName(String lastName) {
		Executor executor = (Executor) eM.createQuery("SELECT e FROM Executor e WHERE e.lastName like :lastName")
	            .setParameter("lastName", lastName)
	            .getSingleResult(); 
		return executor;
	}
	
	@Override
	public List<Executor> getAllExecutors() {
		@SuppressWarnings("unchecked")
		List<Executor> executors = (List<Executor>) eM.createQuery("SELECT e FROM Executor e")
				.getResultList();	            
		return executors;
	}

	@Override
	public void deleteExecutor(Executor executor) {
		executor = eM.find(Executor.class, executor.getId());
		if (executor != null) {
			eM.remove(executor);
		}
	}

	@Override
	public void persist(Executor executor) {
		eM.getTransaction().begin();
		eM.persist(executor);
		eM.getTransaction().commit();
	}
	
	@Override
	public void merge(Executor executor) {
		eM.getTransaction().begin();
		eM.merge(executor);
		eM.getTransaction().commit();
	}

}
