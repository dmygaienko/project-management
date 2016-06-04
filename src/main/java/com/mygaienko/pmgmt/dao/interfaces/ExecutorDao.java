package com.mygaienko.pmgmt.dao.interfaces;

import java.util.List;

import com.mygaienko.pmgmt.model.Executor;
import com.mygaienko.pmgmt.model.Task;

public interface ExecutorDao extends Dao<Executor> {
	
	Executor getById(int id);
	
	Executor getExecutorByLastName(String lastName);
	
	List<Executor> getAll();
	
	void delete(Executor executor);
	
	void persist(Executor executor);

	void merge(Executor executor);

}
