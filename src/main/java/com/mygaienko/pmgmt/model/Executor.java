package com.mygaienko.pmgmt.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class Executor {
	
	@Id 
	@GeneratedValue
	private long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;	
	
	@ManyToMany(targetEntity=Task.class,
	        cascade={CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name="EXECUTOR_TASK",
	        joinColumns={@JoinColumn(name="EXECUTOR_ID")},
	        inverseJoinColumns={@JoinColumn(name="TASK_ID")})
	private List<Task> tasks = new ArrayList<>();
	
	public String getFirstName() {
		return firstName;
	}
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	public void addTask(Task task) {
		tasks.add(task);
	}
	
	public String toString() {
		return firstName + " " + lastName;
	}

	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}
}
