package com.mygaienko.pmgmt.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.mygaienko.pmgmt.context.Context;
import com.mygaienko.pmgmt.controller.interfaces.Screenable;
import com.mygaienko.pmgmt.screenframework.ScreensMediator;
import com.mygaienko.pmgmt.screenframework.ScreensFramework;
import org.joda.time.DateTime;

import com.mygaienko.pmgmt.model.Executor;
import com.mygaienko.pmgmt.model.Project;
import com.mygaienko.pmgmt.model.Task;
import com.mygaienko.pmgmt.service.interfaces.TaskService;
import com.mygaienko.pmgmt.service.TaskServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 * 
 * @author Angie
 */
public class TasksController implements Initializable, Screenable {
	private TaskService taskSvc = TaskServiceImpl.getInstance(); 
	private Project selectedProject;
	private Task selectedTask;
	private ObservableList<Task> obsTasks = FXCollections.observableArrayList();
	private ObservableList<Executor> obsExecutors;
	ScreensMediator myController;
	
	@FXML
	ListView<Task> tasksView;

	@FXML
	ListView<Executor> executorsView;

	@FXML
	Label projectId;

	@FXML
	TextField descriptionId;

	@FXML
	TextField startDateId;

	@FXML
	TextField endDateId;
	
	@FXML
	Button saveTaskChangesBut;
	
	@FXML
	Button deleteTaskBut;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}

	@Override
	public void initScreen() {
		selectedProject = (Project) Context.get(Context.SELECTED_PROJECT);
		obsTasks.clear();
		obsTasks.addAll(selectedProject.getTasks());
		tasksView.setItems(obsTasks);
		if (selectedTask == null) {
			saveTaskChangesBut.setDisable(true);
			deleteTaskBut.setDisable(true);
		}
		if (obsExecutors!= null){
			obsExecutors.clear();
		}
	}

	public void setScreenParent(ScreensMediator screenParent) {
		myController = screenParent;

	}

	@FXML
	private void saveTaskChanges(ActionEvent event) {
		selectedTask = (Task) tasksView.getSelectionModel().getSelectedItem();
		if (selectedTask == null) return;
		obsTasks.remove(selectedTask);
		selectedTask.setDescription(descriptionId.getText());
		selectedTask.setStartDate(new DateTime(startDateId.getText()));
		selectedTask.setEndDate(new DateTime(endDateId.getText()));
		obsTasks.add(selectedTask);
	}

	@FXML
	private void createNewTask(ActionEvent event) {
		Task newTask = new Task();
		newTask.setDescription(descriptionId.getText());
		newTask.setStartDate(new DateTime(startDateId.getText()));
		newTask.setEndDate(new DateTime(endDateId.getText()));
		newTask.setProject(selectedProject);
		
		obsTasks.add(newTask);
		selectedProject.addTask(newTask);
		
		taskSvc.persist(newTask);
		
		tasksView.getSelectionModel().select(newTask);
		Context.put(Context.SELECTED_TASK, newTask);
	}

	@FXML
	private void deleteTask(ActionEvent event) {
		selectedTask = (Task) tasksView.getSelectionModel().getSelectedItem();
		if (selectedTask == null) return;
		obsTasks.remove(selectedTask);
		selectedProject.deleteTask(selectedTask);
		
		List<Executor> executors = selectedTask.getExecutors();
		taskSvc.deleteTask(selectedTask);
		Context.delete(Context.SELECTED_TASK);
		if (obsExecutors != null) {
			obsExecutors.clear();
		}
		
	}

	@FXML
	private void goToExecutors(ActionEvent event) {
		myController.setScreen(ScreensFramework.executorsScreen);
	}

	@FXML
	private void goToProject(ActionEvent event) {
		myController.setScreen(ScreensFramework.projectScreen);
	}

	@FXML
	private void logoff(ActionEvent event) {
		myController.setScreen(ScreensFramework.loginScreen);
	}

	@FXML
	private void selectTask(MouseEvent event) {
		selectedTask = (Task) tasksView.getSelectionModel().getSelectedItem();
		if (selectedTask == null) return;
		saveTaskChangesBut.setDisable(false);
		deleteTaskBut.setDisable(false);
		Context.put(Context.SELECTED_TASK, selectedTask);
		setSldProjProperties();
		if (selectedTask.getExecutors() != null) {
			obsExecutors = FXCollections
					.observableArrayList(selectedTask.getExecutors());
			executorsView.setItems(obsExecutors);
		}
	}

	private void setSldProjProperties() {
		projectId.setText(selectedProject.getName());
		descriptionId.setText(selectedTask.getDescription());
		startDateId.setText(selectedTask.getStartDate().toString("yy-MM-dd"));
		endDateId.setText(selectedTask.getEndDate().toString("yy-MM-dd"));
	}
}
