package com.mygaienko.pmgmt.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.mygaienko.pmgmt.controller.interfaces.Screenable;
import com.mygaienko.pmgmt.model.Executor;
import com.mygaienko.pmgmt.model.Task;
import com.mygaienko.pmgmt.context.Context;
import com.mygaienko.pmgmt.screenframework.ScreensMediator;
import com.mygaienko.pmgmt.screenframework.ScreensFramework;
import com.mygaienko.pmgmt.service.interfaces.ExecutorService;
import com.mygaienko.pmgmt.service.ExecutorServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ExecutorsController implements Initializable, Screenable {
	private ExecutorService execSvc = ExecutorServiceImpl.getInstance(); 
	private Task selectedTask;
	private Executor selectedExecutor;
	private ObservableList<Executor> obsExecutors = FXCollections.observableArrayList();
	private ObservableList<Task> obsTasks = FXCollections.observableArrayList();
	private ScreensMediator mediator;

	@FXML
	ListView<Executor> executorsView;

	@FXML
	ListView<Task> tasksView;

	@FXML
	TextField firstNameId;

	@FXML
	TextField lastNameId;
	
	@FXML
	Button appointForTaskBut;
	
	@FXML
	Button saveChangesBut;
	
	@FXML
	Button deleteExecutorBut;
	
	@Override
	public void setScreenParent(ScreensMediator screenParent) {
		mediator = screenParent;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	public void initScreen(){	
		selectedTask = (Task) Context.get(Context.SELECTED_TASK);
		executorsView.setItems(obsExecutors);
		obsTasks.clear();
		tasksView.setItems(obsTasks);
		if (selectedTask == null) {
			appointForTaskBut.setDisable(true);
			saveChangesBut.setDisable(true);
			deleteExecutorBut.setDisable(true);
			return;
		}
		appointForTaskBut.setDisable(false);
		saveChangesBut.setDisable(false);
		deleteExecutorBut.setDisable(false);
	}

	@FXML
	private void handleExecutorSelection(MouseEvent mouse){
		selectedExecutor = (Executor) executorsView.getSelectionModel().getSelectedItem();
		if (selectedExecutor == null) return;
		saveChangesBut.setDisable(false);
		deleteExecutorBut.setDisable(false);
		obsTasks.clear();
		obsTasks.addAll(selectedExecutor.getTasks());
	}

	@FXML
	private void saveChanges(ActionEvent event) {
		selectedExecutor = (Executor) executorsView.getSelectionModel().getSelectedItem();		
		if (selectedExecutor == null) return;
		obsExecutors.remove(selectedExecutor);
		selectedExecutor.setFirstName(firstNameId.getText());
		selectedExecutor.setLastName(lastNameId.getText());	
		obsExecutors.add(selectedExecutor);
		
		execSvc.persist(selectedExecutor);
	}

	@FXML
	private void createNewExecutor(ActionEvent event) {
		selectedExecutor = new Executor();
		selectedExecutor.setFirstName(firstNameId.getText());
		selectedExecutor.setLastName(lastNameId.getText());	
		obsExecutors.add(selectedExecutor);
	}

	@FXML
	private void deleteExecutor(ActionEvent event) {
		selectedExecutor = (Executor) executorsView.getSelectionModel().getSelectedItem();		
		if (selectedExecutor == null) return;
//		TaskExecutor.deleteByExecutor(selectedExecutor);
	/*	Executor.deleteExecutor(selectedExecutor);*/
		obsExecutors.remove(selectedExecutor);
		obsTasks.clear();
	}

	@FXML
	private void appointForTask(ActionEvent event) {
		selectedExecutor = (Executor) executorsView.getSelectionModel().getSelectedItem();
		if (selectedExecutor == null) return;
		obsExecutors.remove(selectedExecutor);
		selectedExecutor.addTask(selectedTask);
//		selectedTask.addExecutor(selectedExecutor);
		obsExecutors.add(selectedExecutor);
		obsTasks.add(selectedTask);
		
		execSvc.merge(selectedExecutor);
	}

	
	@FXML
	private void goToTasks(ActionEvent event) {
		mediator.setScreen(ScreensFramework.tasksScreen);
	}

	@FXML
	private void logoff(ActionEvent event) {
		mediator.setScreen(ScreensFramework.loginScreen);
	}
}
