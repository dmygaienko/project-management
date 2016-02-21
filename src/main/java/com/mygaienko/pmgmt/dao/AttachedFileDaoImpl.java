package com.mygaienko.pmgmt.dao;

import com.mygaienko.pmgmt.dao.interfaces.AttachedFileDao;
import com.mygaienko.pmgmt.model.AttachedFile;
import com.mygaienko.pmgmt.utils.EntityManagerSingleton;

import javax.persistence.EntityManager;
import java.util.List;

public class AttachedFileDaoImpl implements AttachedFileDao {
	
	private EntityManager eM = EntityManagerSingleton.getEntityManager();
	
	@Override
	public AttachedFile get(int id) {
		AttachedFile file = eM.find(AttachedFile.class, id);
		return file;
	}

	@Override
	public void delete(AttachedFile file) {
		AttachedFile toRemove = eM.find(AttachedFile.class, file.getId());
		if (toRemove != null) {
			eM.remove(toRemove);
		}
	}
	
	@Override
	public void persist(AttachedFile file) {
		eM.getTransaction().begin();
		eM.persist(file);
		eM.getTransaction().commit();
	}
	
	@Override
	public List<AttachedFile> getAll() {
		@SuppressWarnings("unchecked")
		List<AttachedFile> files = (List<AttachedFile>) eM.createQuery("SELECT t FROM AttachedFile t")
				.getResultList();	            
		return files;
	}

}
