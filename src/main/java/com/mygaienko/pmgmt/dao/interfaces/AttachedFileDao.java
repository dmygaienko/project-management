package com.mygaienko.pmgmt.dao.interfaces;

import com.mygaienko.pmgmt.model.AttachedFile;
import com.mygaienko.pmgmt.model.Task;

import java.util.List;

public interface AttachedFileDao {
	
	AttachedFile get(int id);
	
	void delete(AttachedFile file);
	
	void persist(AttachedFile file);

	List<AttachedFile> getAll();
}
