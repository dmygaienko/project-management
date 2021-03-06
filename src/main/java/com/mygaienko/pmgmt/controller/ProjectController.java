package com.mygaienko.pmgmt.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.mygaienko.pmgmt.context.Context;
import com.mygaienko.pmgmt.controller.interfaces.Screenable;
import com.mygaienko.pmgmt.screenframework.*;

import com.mygaienko.pmgmt.model.Project;
import com.mygaienko.pmgmt.service.interfaces.ProjectService;
import com.mygaienko.pmgmt.service.ProjectServiceImpl;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import static com.mygaienko.pmgmt.utils.DateUtils.dateToString;
import static com.mygaienko.pmgmt.utils.DateUtils.parseDate;

/**
 * FXML ProjectController class
 * 
 * @author dmygaien
 */
public class ProjectController implements Initializable, Screenable {
	private ProjectService projectSvc = ProjectServiceImpl.getInstance(); 
	private Project selectedProject;
	private ScreensMediator mediator;

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
		startDateId.setText(dateToString(selectedProject.getStartDate()));
		endDateId.setText(dateToString(selectedProject.getEndDate()));
	}
	
	public void setScreenParent(ScreensMediator screenParent) {
		mediator = screenParent;
	}

	@FXML
	private void logoff(ActionEvent event) {
		mediator.setScreen(Main.loginScreen);
	}

	@FXML
	private void goToProjects(ActionEvent event) {
		mediator.setScreen(Main.projectsScreen);
	}

	@FXML
	private void goToTasks(ActionEvent event) {
		mediator.setScreen(Main.tasksScreen);
	}

	@FXML
	private void saveChanges(ActionEvent event) {
		selectedProject.setName(nameId.getText());
		selectedProject.setDesription(descriptionId.getText());
		selectedProject.setStartDate(parseDate(startDateId.getText()));
		selectedProject.setEndDate(parseDate(endDateId.getText()));
		mediator.setScreen(Main.projectsScreen);
		
		projectSvc.merge(selectedProject);
	}

	@FXML
	private void createNewProject(ActionEvent event) {
		Project project = new Project();
		project.setName(nameId.getText());
		project.setDesription(descriptionId.getText());
		project.setStartDate(parseDate(startDateId.getText()));
		project.setEndDate(parseDate(endDateId.getText()));
		
		projectSvc.persist(project);

		// add project to observable list
		((ProjectsController) mediator
				.getController((Main.projectsScreen)))
				.addProject(project);
		mediator.setScreen(Main.projectsScreen);
	}
}
