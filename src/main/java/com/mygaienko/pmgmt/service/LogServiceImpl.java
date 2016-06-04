package com.mygaienko.pmgmt.service;

import com.mygaienko.pmgmt.dao.LogDaoImpl;
import com.mygaienko.pmgmt.dao.interfaces.LogDao;
import com.mygaienko.pmgmt.model.Log;
import com.mygaienko.pmgmt.model.Task;
import com.mygaienko.pmgmt.service.interfaces.LogService;

import java.util.List;

public class LogServiceImpl implements LogService {

	private static final LogService instance = new LogServiceImpl();

	private LogDao logDao = new LogDaoImpl();

	private LogServiceImpl() {}
	
	public static LogService getInstance() {
		return instance;
	}

	@Override
	public void persist(Log log) {
		logDao.persist(log);
	}

	@Override
	public void delete(Log log) {
		logDao.delete(log);
	}

	@Override
	public List<Log> getLogsByTask(Task task) {
		return logDao.getByTask(task);
	}

}
