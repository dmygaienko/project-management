package com.mygaienko.pmgmt.context;

import java.util.HashMap;
import java.util.Map;

public class Context {
	public static String SELECTED_PROJECT = "selectedProject";
	public static String SELECTED_TASK = "selectedTask";
	public static String SELECTED_EXECUTOR = "selectedExecutor";
	
	private static Map <String, Object> context = new HashMap<>();
	
	public static void put(String key, Object value){
		context.put(key, value);
	}
	
	public static Object get(String key) {
		return context.get(key);
	}
	
	public static void delete(String key) {
		context.remove(key);
	}
	
}	
