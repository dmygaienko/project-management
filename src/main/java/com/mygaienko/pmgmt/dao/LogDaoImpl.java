package com.mygaienko.pmgmt.dao;

import com.mygaienko.pmgmt.dao.interfaces.LogDao;
import com.mygaienko.pmgmt.model.Log;
import com.mygaienko.pmgmt.model.Task;
import com.mygaienko.pmgmt.utils.EntityManagerSingleton;

import javax.persistence.EntityManager;
import java.util.List;

public class LogDaoImpl implements LogDao {
	
	private EntityManager eM = EntityManagerSingleton.getEntityManager();
	
	@Override
	public Log getById(int id) {
		Log task = eM.find(Log.class, id);
		return task;
	}

	@Override
	public void delete(Log log) {
		eM.getTransaction().begin();
		log = eM.find(Log.class, log.getId());
		if (log != null) {
			eM.remove(log);
		}
		eM.getTransaction().commit();
	}
	
	@Override
	public void persist(Log log) {
		eM.getTransaction().begin();
		eM.persist(log);
		eM.getTransaction().commit();
	}

	@Override
	public void merge(Log log) {
		eM.getTransaction().begin();
		eM.merge(log);
		eM.getTransaction().commit();
	}

	@Override
	public void refresh(Log log) {
		eM.refresh(log);
	}
	
	@Override
	public List<Log> getAll() {
		@SuppressWarnings("unchecked")
		List<Log> logs = (List<Log>) eM.createQuery("SELECT t FROM Log t")
				.getResultList();	            
		return logs;
	}

	@Override
	public List<Log> getByTask(Task task) {
		@SuppressWarnings("unchecked")
		List<Log> logs = (List<Log>) eM.createQuery("SELECT l FROM Log l WHERE l.task like :taskId")
				.setParameter("taskId", task)
				.getResultList();

		return logs;
	}

}
