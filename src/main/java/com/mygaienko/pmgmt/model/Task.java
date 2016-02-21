package com.mygaienko.pmgmt.model;

import java.io.File;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

@Entity
public class Task {
	
	@Id
	@GeneratedValue
	private long id;
	
	@Column(name="name")
	private String name;
	
	@Column(name="description")
	private String description;
	
	@Column(name="startDate")
	private DateTime startDate;
	
	@Column(name="endDate")
	private DateTime endDate;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name="project_fk")
	private Project project;
	
	@ManyToMany(
	        cascade = {CascadeType.PERSIST, CascadeType.MERGE},
	        mappedBy = "tasks",
	        targetEntity = Executor.class
	    )
	private List<Executor> executors;

	@OneToMany(mappedBy = "task")
	private List<AttachedFile> attachedFiles;
	
	public String getName() {
		return name;
	}
	
	public long getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public DateTime getStartDate() {
		return startDate;
	}

	public void setStartDate(DateTime startDate) {
		this.startDate = startDate;
	}
	
	@Column(name="endDate")
	public DateTime getEndDate() {
		return endDate;
	}

	public void setEndDate(DateTime endDate) {
		this.endDate = endDate;
	}

	public List<Executor> getExecutors() {
		return executors;
	}
	
	public void setExecutors(List<Executor> executors) {
		this.executors = executors;
	}
	
	@Override
	public String toString(){
		return description;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<AttachedFile> getAttachedFiles() {
		return attachedFiles;
	}

	public void setAttachedFiles(List<AttachedFile> attachedFiles) {
		this.attachedFiles = attachedFiles;
	}
}
