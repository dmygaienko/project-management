package com.mygaienko.pmgmt.service;

import java.util.List;

import com.mygaienko.pmgmt.dao.TaskDao;
import com.mygaienko.pmgmt.dao.TaskDaoImpl;
import com.mygaienko.pmgmt.model.Task;

public class TaskServiceImpl implements TaskService {
	private static TaskServiceImpl instance;
	private TaskDao tDao = new TaskDaoImpl();
	
	private TaskServiceImpl() {}
	
	public static TaskServiceImpl getInstance() {
		if (instance != null) {
		} else {
			instance = new TaskServiceImpl();
		}
		return instance;
	}
	
	public Task getTaskById(int id){
		return tDao.getTaskById(id);
	}
	
	public void deleteTask(Task task){
		tDao.deleteTask(task);
	}
	
	public void persist(Task task){
		tDao.persist(task);
	}

	public List<Task> getAllTasks(){
		return tDao.getAllTasks();
	}
}