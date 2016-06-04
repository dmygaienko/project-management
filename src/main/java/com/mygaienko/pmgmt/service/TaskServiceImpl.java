package com.mygaienko.pmgmt.service;

import java.util.List;

import com.mygaienko.pmgmt.dao.AttachedFileDaoImpl;
import com.mygaienko.pmgmt.dao.interfaces.AttachedFileDao;
import com.mygaienko.pmgmt.dao.interfaces.TaskDao;
import com.mygaienko.pmgmt.dao.TaskDaoImpl;
import com.mygaienko.pmgmt.model.AttachedFile;
import com.mygaienko.pmgmt.model.Task;
import com.mygaienko.pmgmt.service.interfaces.TaskService;

public class TaskServiceImpl implements TaskService {

	private static TaskServiceImpl instance;

	private TaskDao taskDao = new TaskDaoImpl();
	private AttachedFileDao fileDao = new AttachedFileDaoImpl();
	
	private TaskServiceImpl() {}
	
	public static TaskServiceImpl getInstance() {
		if (instance != null) {
		} else {
			instance = new TaskServiceImpl();
		}
		return instance;
	}
	
	public Task getTaskById(int id){
		return taskDao.getTaskById(id);
	}
	
	public void deleteTask(Task task){
		taskDao.deleteTask(task);
	}
	
	public void persist(Task task){
		taskDao.persist(task);
	}

	public void merge(Task task){
		taskDao.merge(task);
	}

	public void refresh(Task task){
		taskDao.refresh(task);
	}

	public List<Task> getAllTasks(){
		return taskDao.getAllTasks();
	}

	@Override
	public void attachFile(AttachedFile file) {
		fileDao.persist(file);
	}

	@Override
	public void deleteFile(AttachedFile file) {
		fileDao.delete(file);
	}

}
