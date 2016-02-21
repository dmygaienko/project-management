package com.mygaienko.pmgmt.controller;

import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.mygaienko.pmgmt.context.Context;
import com.mygaienko.pmgmt.controller.interfaces.Screenable;
import com.mygaienko.pmgmt.model.AttachedFile;
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
	private TaskService taskService = TaskServiceImpl.getInstance();

	private ObservableList<Task> obsTasks = FXCollections.observableArrayList();
	private ObservableList<Executor> obsExecutors;
	private ObservableList<AttachedFile> obsFiles = FXCollections.observableArrayList();


	private Project selectedProject;
	private Task selectedTask;
	private AttachedFile selectedFile;

	private ScreensMediator mediator;
	
	@FXML
	ListView<Task> tasksView;

	@FXML
	ListView<Executor> executorsView;

	@FXML
	ListView<AttachedFile> attachedFilesView;

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
		mediator = screenParent;

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
		
		taskService.persist(newTask);
		
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
		taskService.deleteTask(selectedTask);
		Context.delete(Context.SELECTED_TASK);
		if (obsExecutors != null) {
			obsExecutors.clear();
		}
		
	}

	@FXML
	private void goToExecutors(ActionEvent event) {
		mediator.setScreen(ScreensFramework.executorsScreen);
	}

	@FXML
	private void goToProject(ActionEvent event) {
		mediator.setScreen(ScreensFramework.projectScreen);
	}

	@FXML
	private void logoff(ActionEvent event) {
		mediator.setScreen(ScreensFramework.loginScreen);
	}

	@FXML
	private void selectTask(MouseEvent event) {
		selectedTask = (Task) tasksView.getSelectionModel().getSelectedItem();
		if (selectedTask == null) return;
		saveTaskChangesBut.setDisable(false);
		deleteTaskBut.setDisable(false);
		Context.put(Context.SELECTED_TASK, selectedTask);
		setSldProjProperties();
		fillExecutorsView();
		fillAttachedFilesView();
	}

	private void fillAttachedFilesView() {
		if (selectedTask.getAttachedFiles() != null) {
			obsFiles = FXCollections
					.observableArrayList(selectedTask.getAttachedFiles());
			attachedFilesView.setItems(obsFiles);
		}
	}

	private void fillExecutorsView() {
		if (selectedTask.getExecutors() != null) {
			obsExecutors = FXCollections
					.observableArrayList(selectedTask.getExecutors());
			executorsView.setItems(obsExecutors);
		}
	}

	@FXML
	private void selectFile(MouseEvent event) {
		selectedFile = (AttachedFile) attachedFilesView.getSelectionModel().getSelectedItem();
	}

	@FXML
	private void attachFile(ActionEvent event) throws IOException {
		if (selectedTask == null) return;

		File file = mediator.showOpenDialog();
		FileInputStream inputStream = new FileInputStream(file);
		byte[] fileData = new byte[(int) file.length()];
		inputStream.read(fileData);
		inputStream.close();

		taskService.attachFile(new AttachedFile(fileData, file.getName(), selectedTask));
	}

	@FXML
	private void downloadFile(ActionEvent event) throws IOException {
		selectedFile = (AttachedFile) attachedFilesView.getSelectionModel().getSelectedItem();
		if (selectedFile == null) return;

		File file = mediator.showSaveDialog();

		FileOutputStream writer = new FileOutputStream(file);
		writer.write(selectedFile.getData());
		writer.close();
	}

	@FXML
	private void deleteFile(ActionEvent event) throws IOException {

	}

	private void setSldProjProperties() {
		projectId.setText(selectedProject.getName());
		descriptionId.setText(selectedTask.getDescription());
		startDateId.setText(selectedTask.getStartDate().toString("yy-MM-dd"));
		endDateId.setText(selectedTask.getEndDate().toString("yy-MM-dd"));
	}
}
