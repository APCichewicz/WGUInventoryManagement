package com.example.wgusoftware1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**

 The InventoryManagementSystem class is the entry point for the application.

 It sets up and launches the main GUI window of the Inventory Management System.
 */
public class InventoryManagementSystem extends Application {

    /**

     The start method is called by the JavaFX runtime when the application is launched.

     It loads the FXML file that defines the main GUI window, sets its title and scene, and shows the window.

     @param primaryStage the primary stage for the application, which is the main window of the Inventory Management System

     @throws Exception if an error occurs while loading the FXML file
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("InventoryManagementSystem.fxml"));
        Parent root = loader.load();

        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     The main method is the entry point for the application.
     It simply calls the launch method of the Application class to start the JavaFX runtime.
     @param args command line arguments (unused)
     */
    public static void main(String[] args) {
        launch();
    }
}