
package com.mygaienko.pmgmt.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.joda.time.DateTime;

@Entity
public class Project {
	@Id
	@GeneratedValue
	private int id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="start_date")
	private DateTime startDate;
	
	@Column(name="end_date")
	private DateTime endDate;
	
	@Column(name="description")
	private String description;
	
	@OneToMany(orphanRemoval=true, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, mappedBy="project")
	private List<Task> tasks = new ArrayList<>();
	
	public Project(String name, String description, DateTime startDate,
			DateTime endDate) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
	}
	
	public Project(){}	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public DateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}
	public DateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}
	
	public String getDesription() {
		return description;
	}
	public void setDesription(String desription) {
		this.description = desription;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Task> getTasks() {
		return tasks;
	}
	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	public void addTask(Task task) {
		tasks.add(task);		
	}
	
	public void deleteTask(Task task) {
		tasks.remove(task);		
	}

}
