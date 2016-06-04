package com.mygaienko.pmgmt.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.mygaienko.pmgmt.controller.interfaces.Screenable;
import com.mygaienko.pmgmt.model.Executor;
import com.mygaienko.pmgmt.model.Task;
import com.mygaienko.pmgmt.context.Context;
import com.mygaienko.pmgmt.screenframework.Main;
import com.mygaienko.pmgmt.screenframework.ScreensMediator;
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
	private ExecutorService executorService = ExecutorServiceImpl.getInstance();
	private Task selectedTask;
	private Executor selectedExecutor;
	private ObservableList<Executor> executorsObservable = FXCollections.observableArrayList();
	private ObservableList<Task> tasksObservable = FXCollections.observableArrayList();
	private ScreensMediator mediator;

	@FXML
	ListView<Executor> executorListView;

	@FXML
	ListView<Task> tasksListView;

	@FXML
	TextField firstNameText;

	@FXML
	TextField lastNameText;
	
	@FXML
	Button appointForTaskBut;

	@FXML
	Button deleteExecutorBut;
	
	@FXML
	Button saveChangesBut;

	@Override
	public void setScreenParent(ScreensMediator screenParent) {
		mediator = screenParent;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		executorListView.setItems(executorsObservable);
		tasksListView.setItems(tasksObservable);
	}
	
	public void initScreen(){	
		selectedTask = (Task) Context.get(Context.SELECTED_TASK);

		if (selectedTask == null) {
			appointForTaskBut.setDisable(true);
			saveChangesBut.setDisable(true);
			deleteExecutorBut.setDisable(true);
			return;
		}

		appointForTaskBut.setDisable(false);
		saveChangesBut.setDisable(false);
		deleteExecutorBut.setDisable(false);

		tasksObservable.clear();
		executorsObservable.clear();
		executorsObservable.addAll(executorService.getAllExecutors());
	}

	@FXML
	private void selectExecutor(MouseEvent mouse){
		selectedExecutor = (Executor) executorListView.getSelectionModel().getSelectedItem();
		if (selectedExecutor == null) return;
		saveChangesBut.setDisable(false);
		deleteExecutorBut.setDisable(false);

		firstNameText.setText(selectedExecutor.getFirstName());
		lastNameText.setText(selectedExecutor.getLastName());

		tasksObservable.clear();
		tasksObservable.addAll(selectedExecutor.getTasks());
	}

	@FXML
	private void saveChanges(ActionEvent event) {
		selectedExecutor = (Executor) executorListView.getSelectionModel().getSelectedItem();
		if (selectedExecutor == null) return;
		executorsObservable.remove(selectedExecutor);

		selectedExecutor.setFirstName(firstNameText.getText());
		selectedExecutor.setLastName(lastNameText.getText());
		executorsObservable.add(selectedExecutor);
		
		executorService.merge(selectedExecutor);
	}

	@FXML
	private void createNewExecutor(ActionEvent event) {
		selectedExecutor = new Executor();
		selectedExecutor.setFirstName(firstNameText.getText());
		selectedExecutor.setLastName(lastNameText.getText());
		executorsObservable.add(selectedExecutor);

		executorService.persist(selectedExecutor);
	}

	@FXML
	private void deleteExecutor(ActionEvent event) {
		selectedExecutor = (Executor) executorListView.getSelectionModel().getSelectedItem();
		if (selectedExecutor == null) return;
//		TaskExecutor.deleteByExecutor(selectedExecutor);
	/*	Executor.deleteExecutor(selectedExecutor);*/
		executorsObservable.remove(selectedExecutor);
		tasksObservable.clear();

		executorService.deleteExecutor(selectedExecutor);
	}

	@FXML
	private void appointForTask(ActionEvent event) {
		selectedExecutor = (Executor) executorListView.getSelectionModel().getSelectedItem();
		if (selectedExecutor == null) return;
		executorsObservable.remove(selectedExecutor);
		selectedExecutor.addTask(selectedTask);
//		selectedTask.addExecutor(selectedExecutor);
		executorsObservable.add(selectedExecutor);
		tasksObservable.add(selectedTask);
		
		executorService.merge(selectedExecutor);
	}

	
	@FXML
	private void goToTasks(ActionEvent event) {
		mediator.setScreen(Main.tasksScreen);
	}

	@FXML
	private void logoff(ActionEvent event) {
		mediator.setScreen(Main.loginScreen);
	}
}
