package com.mygaienko.pmgmt.controller;


import java.net.URL;
import java.util.ResourceBundle;

import com.mygaienko.pmgmt.controller.interfaces.Screenable;
import com.mygaienko.pmgmt.screenframework.ScreensMediator;
import com.mygaienko.pmgmt.screenframework.ScreensFramework;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;

/**
 * FXML LoginController class
 * 
 * @author dmygaien
 */
public class LoginController implements Initializable, Screenable {
	@FXML
	TextField name;
	@FXML
	PasswordField password;
	@FXML
	Label errorMessage;
	ScreensMediator myController;

	/**
	 * Initializes the controller class.
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		// TODO
	}
	
	public void initScreen(){		
	}

	public void setScreenParent(ScreensMediator screenParent) {
		myController = screenParent;
	}

	@FXML
	private void login(ActionEvent event) {
		if (!authenticate()) {
			errorMessage.setText("wrong name/password");
		} else {
			myController.setScreen(ScreensFramework.projectsScreen);
		}
		
	}

	private boolean authenticate() {
		if (name.getText().equals("a") && password.getText().equals("a")) {
			return true;
		}
		return false;
	}

//	@FXML
//	private void registrate(ActionEvent event) {
//		myController.setScreen(ScreensFramework.projectScreen);
//	}
}