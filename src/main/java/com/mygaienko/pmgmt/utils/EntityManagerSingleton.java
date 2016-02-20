package com.mygaienko.pmgmt.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerSingleton {

	private static EntityManagerSingleton instance;
	private EntityManager eM;

	private EntityManagerSingleton() {
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("oracle");
	
//		emf.getProperties().put("jadira.usertype.autoRegisterUserTypes", "true");
		eM = emf.createEntityManager();
	}

	public static EntityManagerSingleton getInstance() {
		if (instance != null) {
		} else {
			instance = new EntityManagerSingleton();
		}
		return instance;
	}

	public static EntityManager getEntityManager() {
		return getInstance().eM;
	}

}
