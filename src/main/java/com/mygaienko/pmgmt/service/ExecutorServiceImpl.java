package com.mygaienko.pmgmt.service;

import java.util.List;

import com.mygaienko.pmgmt.dao.interfaces.ExecutorDao;
import com.mygaienko.pmgmt.dao.ExecutorDaoImpl;
import com.mygaienko.pmgmt.model.Executor;
import com.mygaienko.pmgmt.service.interfaces.ExecutorService;

public class ExecutorServiceImpl implements ExecutorService {
	private static ExecutorServiceImpl instance;
	private ExecutorDao execDao = new ExecutorDaoImpl();
	
	private ExecutorServiceImpl() {	}
	
	public static ExecutorServiceImpl getInstance() {
		if (instance != null) {
		} else {
			instance = new ExecutorServiceImpl();
		}
		return instance;
	}
	
	@Override
	public Executor getExecutorById(int id) {
		return execDao.getExecutorById(id);
	}
	
	@Override
	public Executor getExecutorByLastName(String lastName) {
		return execDao.getExecutorByLastName(lastName);
	}
	
	@Override
	public List<Executor> getAllExecutors() {
		return execDao.getAllExecutors();
	}

	@Override
	public void deleteExecutor(Executor executor) {
		execDao.deleteExecutor(executor);
	}
	
	@Override
	public void persist(Executor executor) {
		execDao.persist(executor);
	}

	@Override
	public void merge(Executor executor) {
		execDao.merge(executor);
	}
	
	
	
}

