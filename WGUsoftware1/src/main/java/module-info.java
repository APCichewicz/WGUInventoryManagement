/**

 The com.example.wgusoftware1 module contains the main classes and controllers for the WGU Inventory Management System.

 This module requires the javafx.controls and javafx.fxml modules.

 The com.example.wgusoftware1 package is opened to javafx.fxml to allow for proper FXML loading.

 The com.example.wgusoftware1 package is exported to allow access to the main application class from other modules.
 */
module com.example.wgusoftware1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.wgusoftware1 to javafx.fxml;
    exports com.example.wgusoftware1;
}