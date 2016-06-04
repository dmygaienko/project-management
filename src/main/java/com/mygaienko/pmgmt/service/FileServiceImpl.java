package com.mygaienko.pmgmt.service;

import com.mygaienko.pmgmt.dao.AttachedFileDaoImpl;
import com.mygaienko.pmgmt.dao.LogDaoImpl;
import com.mygaienko.pmgmt.dao.TaskDaoImpl;
import com.mygaienko.pmgmt.dao.interfaces.AttachedFileDao;
import com.mygaienko.pmgmt.dao.interfaces.LogDao;
import com.mygaienko.pmgmt.dao.interfaces.TaskDao;
import com.mygaienko.pmgmt.model.AttachedFile;
import com.mygaienko.pmgmt.model.Log;
import com.mygaienko.pmgmt.model.Task;
import com.mygaienko.pmgmt.service.interfaces.FileService;
import com.mygaienko.pmgmt.service.interfaces.TaskService;

import java.util.List;

public class FileServiceImpl implements FileService {

	private final static FileService instance = new FileServiceImpl();

	private AttachedFileDao fileDao = new AttachedFileDaoImpl();

	private FileServiceImpl() {}
	
	public static FileService getInstance() {
		return instance;
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
