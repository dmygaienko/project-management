package com.mygaienko.pmgmt.controller;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

import com.mygaienko.pmgmt.context.Context;
import com.mygaienko.pmgmt.controller.interfaces.Screenable;
import com.mygaienko.pmgmt.model.*;
import com.mygaienko.pmgmt.screenframework.ScreensMediator;
import com.mygaienko.pmgmt.screenframework.Main;
import com.mygaienko.pmgmt.service.FileServiceImpl;
import com.mygaienko.pmgmt.service.LogServiceImpl;
import com.mygaienko.pmgmt.service.interfaces.FileService;
import com.mygaienko.pmgmt.service.interfaces.LogService;
import javafx.scene.control.*;
import org.joda.time.DateTime;

import com.mygaienko.pmgmt.service.interfaces.TaskService;
import com.mygaienko.pmgmt.service.TaskServiceImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 * 
 * @author Angie
 */
public class TasksController implements Initializable, Screenable {

	private TaskService taskService = TaskServiceImpl.getInstance();
	private FileService fileService = FileServiceImpl.getInstance();
	private LogService logService = LogServiceImpl.getInstance();

	private Project selectedProject;
	private Task selectedTask;
	private AttachedFile selectedFile;

	private ScreensMediator mediator;
	
	@FXML
	ListView<Task> tasksListView;
    private ObservableList<Task> tasksObservable = FXCollections.observableArrayList();

	@FXML
	ListView<Executor> executorsListView;
    private ObservableList<Executor> executorsObservable = FXCollections.observableArrayList();

	@FXML
	ListView<AttachedFile> filesListView;
    private ObservableList<AttachedFile> filesObservable = FXCollections.observableArrayList();

	@FXML
	Label projectId;

	@FXML
	TextField descriptionId;

	@FXML
	TextField startDateId;

	@FXML
	TextField endDateId;

    @FXML
    ComboBox<TaskStatus> statusComboBox;
	
	@FXML
	Button saveTaskChangesBut;

    @FXML
    Button deleteTaskBut;

    /*Logs*/
	@FXML
    ListView<Log> logsListView;
    private ObservableList<Log> logsObservable = FXCollections.observableArrayList();

    @FXML
    TextField logHoursField;

    @FXML
    TextField logDayField;

    @FXML
    ComboBox<Executor> logExecutorComboBox;

    @FXML
    Button createLogBut;

    @FXML
    Button deleteLogBut;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		tasksListView.setItems(tasksObservable);
        filesListView.setItems(filesObservable);
        executorsListView.setItems(executorsObservable);
        logsListView.setItems(logsObservable);
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
        statusComboBox.setItems(FXCollections.observableArrayList(TaskStatus.values()));
	}

	public void setScreenParent(ScreensMediator screenParent) {
		mediator = screenParent;
	}

	@FXML
	private void saveTaskChanges(ActionEvent event) {
		selectedTask = getSelectedTask();
		if (selectedTask == null) return;
		//tasksObservable.remove(selectedTask);
		selectedTask.setDescription(descriptionId.getText());
		selectedTask.setStartDate(new DateTime(startDateId.getText()));
		selectedTask.setEndDate(new DateTime(endDateId.getText()));
		selectedTask.setStatus(statusComboBox.getValue());
		//tasksObservable.add(selectedTask);

		taskService.merge(selectedTask);
		refreshTasksObservable();
	}

	private void refreshTasksObservable() {
		tasksObservable.clear();
		tasksObservable.addAll(taskService.getByProject(selectedProject));
	}

	@FXML
	private void createNewTask(ActionEvent event) {
		Task newTask = new Task();
		newTask.setDescription(descriptionId.getText());
		newTask.setStartDate(new DateTime(startDateId.getText()));
		newTask.setEndDate(new DateTime(endDateId.getText()));
		newTask.setProject(selectedProject);
		newTask.setStatus(TaskStatus.NOT_STARTED);
		taskService.persist(newTask);

		refreshTasksObservable();

		tasksListView.getSelectionModel().select(newTask);
		Context.put(Context.SELECTED_TASK, newTask);
	}

	@FXML
	private void deleteTask(ActionEvent event) {
		selectedTask = getSelectedTask();
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
		selectedTask = getSelectedTask();
		if (selectedTask == null) return;
		taskService.refresh(selectedTask);

		saveTaskChangesBut.setDisable(false);
		deleteTaskBut.setDisable(false);
		Context.put(Context.SELECTED_TASK, selectedTask);

		setSelectedProjectProperties();
		fillExecutorsView();
		fillLogsView();
		fillAttachedFilesView();
	}

    private Task getSelectedTask() {
        return tasksListView.getSelectionModel().getSelectedItem();
    }

    private void fillLogsView() {
        logsObservable.clear();
        logsObservable.addAll(logService.getLogsByTask(selectedTask));
    }

    private void refreshAttachedFilesView() {
		taskService.refresh(selectedTask);
		fillAttachedFilesView();
	}

	private void refreshLogsView() {
		taskService.refresh(selectedTask);
		fillLogsView();
	}

	private void fillAttachedFilesView() {
		if (selectedTask.getAttachedFiles() != null) {
            filesObservable.clear();
			filesObservable.addAll(selectedTask.getAttachedFiles());

		}
	}

	private void fillExecutorsView() {
		if (selectedTask.getExecutors() != null) {
            executorsObservable.clear();
			executorsObservable.addAll(selectedTask.getExecutors());

		}
	}

	@FXML
	private void selectFile(MouseEvent event) {
		selectedFile = getSelectedFile();
	}

    private AttachedFile getSelectedFile() {
        return filesListView.getSelectionModel().getSelectedItem();
    }

    private Log getSelectedLog() {
        return logsListView.getSelectionModel().getSelectedItem();
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

		fileService.attachFile(new AttachedFile(fileData, file.getName(), selectedTask));

		refreshAttachedFilesView();
	}

	@FXML
	private void downloadFile(ActionEvent event) throws IOException {
		selectedFile = getSelectedFile();
		if (selectedFile == null) return;

		File file = mediator.showSaveDialog();

		FileOutputStream writer = new FileOutputStream(file);
		writer.write(selectedFile.getData());
		writer.close();
	}

	@FXML
	private void deleteFile(ActionEvent event) throws IOException {
		selectedFile = getSelectedFile();
		if (selectedFile == null) return;

		fileService.deleteFile(selectedFile);
	}

	private void setSelectedProjectProperties() {
		projectId.setText(selectedProject.getName());
		descriptionId.setText(selectedTask.getDescription());
		startDateId.setText(selectedTask.getStartDate().toString("yy-MM-dd"));
		endDateId.setText(selectedTask.getEndDate().toString("yy-MM-dd"));
	}

    @FXML
    private void onSelectExecutor(MouseEvent event) {
        logExecutorComboBox.setItems(FXCollections.observableArrayList(selectedTask.getExecutors()));
    }

    @FXML
    private void selectLog(MouseEvent event) {
        getSelectedLog();
    }

    @FXML
    private void createLog(ActionEvent event) {
        Log log = new Log();
        log.setHours(Integer.valueOf(logHoursField.getText()));
        log.setDate(new DateTime(logDayField.getText()));
        log.setTask(selectedTask);
        log.setExecutor(logExecutorComboBox.getValue());

        logService.persist(log);

		refreshLogsView();
    }

    @FXML
    private void deleteLog(ActionEvent event) {
        logService.delete(getSelectedLog());

		refreshLogsView();
    }

}
