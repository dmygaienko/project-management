package com.mygaienko.pmgmt.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.mygaienko.pmgmt.context.Context;
import com.mygaienko.pmgmt.controller.interfaces.Screenable;
import com.mygaienko.pmgmt.screenframework.Main;
import com.mygaienko.pmgmt.screenframework.ScreensMediator;
import org.joda.time.DateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import com.mygaienko.pmgmt.model.Project;
import com.mygaienko.pmgmt.service.interfaces.ProjectService;
import com.mygaienko.pmgmt.service.ProjectServiceImpl;

import static com.mygaienko.pmgmt.utils.DateUtils.dateToString;

/**
 * FXML ProjectsController class
 * 
 * @author dmygaien
 */
public class ProjectsController implements Initializable, Screenable {
	private ProjectService projectSvc = ProjectServiceImpl.getInstance(); 
	private static final String NAME = "name";
	private static final String DESCRIPTION = "description";
	private Project selectedProject;
	private ObservableList<Project> obsProjects = FXCollections
			.observableArrayList();

	private ScreensMediator mediator;

	@FXML
	ListView projectsView;

	@FXML
	Label descriptionId;

	@FXML
	Label responsMngrId;

	@FXML
	Label startDateId;

	@FXML
	Label endDateId;
	
	@FXML
	Button deleteProjectBut;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		//buildProjects();
		obsProjects.addAll(projectSvc.getAllProject());
	}
	
	public void initScreen() {
		projectsView.setItems(obsProjects);
		deleteProjectBut.setDisable(true);
	}

	public void setScreenParent(ScreensMediator screenParent) {
		mediator = screenParent;
	}

	@FXML
	private void logoff(ActionEvent event) {
		mediator.setScreen(Main.loginScreen);
	}

	@FXML
	private void goToNewProjectScreen(ActionEvent event) {
		Context.delete(Context.SELECTED_PROJECT);
		mediator.setScreen(Main.projectScreen);
	}	

	@FXML
	private void selectProject(MouseEvent mouseEvent) {
		selectedProject = (Project) projectsView
				.getSelectionModel().getSelectedItem();
		Context.put(Context.SELECTED_PROJECT, selectedProject);
		deleteProjectBut.setDisable(false);
		if (mouseEvent.getClickCount() == 2) {
			System.out.println("2 clicks");			
			mediator.setScreen(Main.projectScreen);
		} else if (mouseEvent.getClickCount() == 1) {	
			if (selectedProject == null) return;
			String description = selectedProject.getDesription();
			String startDate = dateToString(selectedProject.getStartDate());
			String endDate = dateToString(selectedProject.getEndDate());
			descriptionId.setText(description != null ? description : "blank");
			startDateId.setText(startDate != null ? startDate : "blank");
			endDateId.setText(endDate != null ? endDate : "blank");
		}
	}
	
	@FXML
	private void deleteProject(ActionEvent event) {
		obsProjects.remove(selectedProject);
		projectSvc.deleteProject(selectedProject);
		Context.delete(Context.SELECTED_EXECUTOR);
		Context.delete(Context.SELECTED_PROJECT);
		Context.delete(Context.SELECTED_TASK);
	}

	private void buildProjects() {
		for (int i = 0; i < 10; i++) {
			obsProjects.add(buildProject(i));
		}
	}

	private Project buildProject(int i) {
		String name = NAME + i;
		String description = DESCRIPTION + i;
		return new Project(name, description, new DateTime(), new DateTime());
	}

	public void addProject(Project project) {
		obsProjects.add(project);
	}
}