package com.mygaienko.pmgmt.controller;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import com.mygaienko.pmgmt.context.Context;
import com.mygaienko.pmgmt.controller.interfaces.Screenable;
import com.mygaienko.pmgmt.model.AttachedFile;
import com.mygaienko.pmgmt.screenframework.ScreensMediator;
import com.mygaienko.pmgmt.screenframework.Main;
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

	private ObservableList<Task> tasksObservable = FXCollections.observableArrayList();
	private ObservableList<Executor> executorsObservable = FXCollections.observableArrayList();
	private ObservableList<AttachedFile> filesObservable = FXCollections.observableArrayList();


	private Project selectedProject;
	private Task selectedTask;
	private AttachedFile selectedFile;

	private ScreensMediator mediator;
	
	@FXML
	ListView<Task> tasksListView;

	@FXML
	ListView<Executor> executorsListView;

	@FXML
	ListView<AttachedFile> filesListView;

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
		tasksListView.setItems(tasksObservable);
	}

	@Override
	public void initScreen() {
		selectedProject = (Project) Context.get(Context.SELECTED_PROJECT);
		executorsObservable.clear();
		refreshTasksObservable();

		if (selectedTask == null) {
			saveTaskChangesBut.setDisable(true);
			deleteTaskBut.setDisable(true);
		}
	}

	public void setScreenParent(ScreensMediator screenParent) {
		mediator = screenParent;
	}

	@FXML
	private void saveTaskChanges(ActionEvent event) {
		selectedTask = tasksListView.getSelectionModel().getSelectedItem();
		if (selectedTask == null) return;
		//tasksObservable.remove(selectedTask);
		selectedTask.setDescription(descriptionId.getText());
		selectedTask.setStartDate(new DateTime(startDateId.getText()));
		selectedTask.setEndDate(new DateTime(endDateId.getText()));
		//tasksObservable.add(selectedTask);

		taskService.merge(selectedTask);
		refreshTasksObservable();
	}

	private void refreshTasksObservable() {
		tasksObservable.clear();
		tasksObservable.addAll(selectedProject.getTasks());
	}

	@FXML
	private void createNewTask(ActionEvent event) {
		Task newTask = new Task();
		newTask.setDescription(descriptionId.getText());
		newTask.setStartDate(new DateTime(startDateId.getText()));
		newTask.setEndDate(new DateTime(endDateId.getText()));
		newTask.setProject(selectedProject);
		
		tasksObservable.add(newTask);
		selectedProject.addTask(newTask);
		
		taskService.persist(newTask);
		
		tasksListView.getSelectionModel().select(newTask);
		Context.put(Context.SELECTED_TASK, newTask);
	}

	@FXML
	private void deleteTask(ActionEvent event) {
		selectedTask = tasksListView.getSelectionModel().getSelectedItem();
		if (selectedTask == null) return;
		tasksObservable.remove(selectedTask);
		selectedProject.deleteTask(selectedTask);
		
		taskService.deleteTask(selectedTask);
		Context.delete(Context.SELECTED_TASK);
		if (executorsObservable != null) {
			executorsObservable.clear();
		}
		
	}

	@FXML
	private void goToExecutors(ActionEvent event) {
		mediator.setScreen(Main.executorsScreen);
	}

	@FXML
	private void goToProject(ActionEvent event) {
		mediator.setScreen(Main.projectScreen);
	}

	@FXML
	private void logoff(ActionEvent event) {
		mediator.setScreen(Main.loginScreen);
	}

	@FXML
	private void selectTask(MouseEvent event) {
		selectedTask = (Task) tasksListView.getSelectionModel().getSelectedItem();
		if (selectedTask == null) return;
		taskService.refresh(selectedTask);

		saveTaskChangesBut.setDisable(false);
		deleteTaskBut.setDisable(false);
		Context.put(Context.SELECTED_TASK, selectedTask);

		setSelectedProjectProperties();
		fillExecutorsView();
		fillAttachedFilesView();
	}

	private void refreshAttachedFilesView() {
		taskService.refresh(selectedTask);
		fillAttachedFilesView();
	}

	private void fillAttachedFilesView() {
		if (selectedTask.getAttachedFiles() != null) {
			filesObservable = FXCollections.observableArrayList(selectedTask.getAttachedFiles());
			filesListView.setItems(filesObservable);
		}
	}

	private void fillExecutorsView() {
		if (selectedTask.getExecutors() != null) {
			executorsObservable = FXCollections
					.observableArrayList(selectedTask.getExecutors());
			executorsListView.setItems(executorsObservable);
		}
	}

	@FXML
	private void selectFile(MouseEvent event) {
		selectedFile = (AttachedFile) filesListView.getSelectionModel().getSelectedItem();
	}

	@FXML
	private void attachFile(ActionEvent event) throws IOException {
		if (selectedTask == null) return;

		File file = mediator.showOpenDialog();
		BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		/*FileInputStream inputStream = new FileInputStream(file);*/
		byte[] fileData = new byte[(int) file.length()];
		inputStream.read(fileData);
		inputStream.close();

		taskService.attachFile(new AttachedFile(fileData, file.getName(), selectedTask));

		refreshAttachedFilesView();
	}

	@FXML
	private void downloadFile(ActionEvent event) throws IOException {
		selectedFile = filesListView.getSelectionModel().getSelectedItem();
		if (selectedFile == null) return;

		File file = mediator.showSaveDialog();

		FileOutputStream writer = new FileOutputStream(file);
		writer.write(selectedFile.getData());
		writer.close();
	}

	@FXML
	private void deleteFile(ActionEvent event) throws IOException {
		selectedFile = filesListView.getSelectionModel().getSelectedItem();
		if (selectedFile == null) return;

		taskService.deleteFile(selectedFile);
	}

	private void setSelectedProjectProperties() {
		projectId.setText(selectedProject.getName());
		descriptionId.setText(selectedTask.getDescription());
		startDateId.setText(selectedTask.getStartDate().toString("yy-MM-dd"));
		endDateId.setText(selectedTask.getEndDate().toString("yy-MM-dd"));
	}
}
