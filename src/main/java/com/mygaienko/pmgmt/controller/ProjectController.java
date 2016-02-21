package com.mygaienko.pmgmt.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.mygaienko.pmgmt.context.Context;
import com.mygaienko.pmgmt.controller.interfaces.Screenable;
import com.mygaienko.pmgmt.screenframework.*;
import org.joda.time.DateTime;

import com.mygaienko.pmgmt.model.Project;
import com.mygaienko.pmgmt.service.interfaces.ProjectService;
import com.mygaienko.pmgmt.service.ProjectServiceImpl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML ProjectController class
 * 
 * @author dmygaien
 */
public class ProjectController implements Initializable, Screenable {
	private ProjectService projectSvc = ProjectServiceImpl.getInstance(); 
	private Project selectedProject;
	ScreensMediator myController;

	@FXML
	TextField nameId;

	@FXML
	TextField descriptionId;

	@FXML
	TextField startDateId;

	@FXML
	TextField endDateId;
	
	@FXML
	Button saveChanges;
	
	@FXML
	Button goToTasks;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {

	}
	
	public void initScreen() {
		selectedProject = (Project) Context.get(Context.SELECTED_PROJECT);
		if (selectedProject == null) {
			saveChanges.setDisable(true);
			goToTasks.setDisable(true);
			nameId.clear();
			descriptionId.clear();
			startDateId.clear();
			endDateId.clear();
			return;
		}
		saveChanges.setDisable(false);
		goToTasks.setDisable(false);
		nameId.setText(selectedProject.getName());
		descriptionId.setText(selectedProject.getDesription());
		startDateId.setText(selectedProject.getStartDate().toString("yy-MM-dd"));
		endDateId.setText(selectedProject.getEndDate().toString("yy-MM-dd"));
	}
	
	public void setScreenParent(ScreensMediator screenParent) {
		myController = screenParent;
	}

	@FXML
	private void logoff(ActionEvent event) {
		myController.setScreen(ScreensFramework.loginScreen);
	}

	@FXML
	private void goToProjects(ActionEvent event) {
		myController.setScreen(ScreensFramework.projectsScreen);
	}

	@FXML
	private void goToTasks(ActionEvent event) {
		myController.setScreen(ScreensFramework.tasksScreen);
	}

	@FXML
	private void saveChanges(ActionEvent event) {
		selectedProject.setName(nameId.getText());
		selectedProject.setDesription(descriptionId.getText());
		selectedProject.setStartDate(new DateTime(startDateId.getText()));
		selectedProject.setEndDate(new DateTime(endDateId.getText()));
		myController.setScreen(ScreensFramework.projectsScreen);
		
		projectSvc.merge(selectedProject);
	}

	@FXML
	private void createNewProject(ActionEvent event) {
		Project project = new Project();
		project.setName(nameId.getText());
		project.setDesription(descriptionId.getText());
		project.setStartDate(new DateTime(startDateId.getText()));
		project.setEndDate(new DateTime(endDateId.getText()));
		
		projectSvc.persist(project);

		// add project to observable list
		((ProjectsController) myController
				.getController((ScreensFramework.projectsScreen)))
				.addProject(project);
		myController.setScreen(ScreensFramework.projectsScreen);
	}
}
