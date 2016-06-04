package com.mygaienko.pmgmt.dao.interfaces;

import java.util.List;

public interface Dao<T> {

	T getById(int id);
	
	void delete(T entity);
	
	void persist(T entity);

	void refresh(T entity);

	List<T> getAll();

	void merge(T entity);
}
