package com.mygaienko.pmgmt.screenframework;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author dmygaien
 */
public class Main extends Application {
    
    public static String loginScreen = "mainScreen";
    public static String loginScreenFile = "/fxml/1_Login.fxml";
    public static String projectsScreen = "projectsScreen";
    public static String projectsFile = "/fxml/2_Projects.fxml";
    public static String projectScreen = "projectScreen";
    public static String projectFile = "/fxml/3_Project.fxml";
    public static String tasksScreen = "tasksScreen";
    public static String tasksFile = "/fxml/4_Tasks.fxml";
    public static String executorsScreen = "executorsScreen";
    public static String executorsFile = "/fxml/5_Executors.fxml";
    
    
    @Override
    public void start(Stage primaryStage) {
        
        ScreensMediator mainContainer = new ScreensMediator(primaryStage);
        //load all screens
        mainContainer.loadScreen(Main.loginScreen, Main.loginScreenFile);
        mainContainer.loadScreen(Main.projectsScreen, Main.projectsFile);
        mainContainer.loadScreen(Main.projectScreen, Main.projectFile);
        mainContainer.loadScreen(Main.tasksScreen, Main.tasksFile);
        mainContainer.loadScreen(Main.executorsScreen, Main.executorsFile);
        
        //set first main screen
        mainContainer.setScreen(Main.loginScreen);
        
        AnchorPane root = new AnchorPane();
        root.getStyleClass().add("main-panel");
        root.getChildren().addAll(mainContainer);
        Scene scene = new Scene(root);        
        scene.getStylesheets().add("/styles/styles.css");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

	/**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}