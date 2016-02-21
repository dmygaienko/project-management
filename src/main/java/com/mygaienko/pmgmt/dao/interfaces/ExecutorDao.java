package com.mygaienko.pmgmt.dao.interfaces;

import java.util.List;

import com.mygaienko.pmgmt.model.Executor;
import com.mygaienko.pmgmt.model.Task;

public interface ExecutorDao {
	
	Executor getExecutorById(int id);
	
	Executor getExecutorByLastName(String lastName);
	
	List<Executor> getAllExecutors();
	
	void deleteExecutor(Executor executor);
	
	void persist(Executor executor);

	void merge(Executor executor);

}
