package com.example.wgusoftware1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
/**
 * The controller class for the Add Part view.
 */
public class AddPartController {
    @FXML
    private TextField partID;
    @FXML
    private TextField partName;
    @FXML
    private TextField partInv;
    @FXML
    private TextField partPrice;
    @FXML
    private TextField partMax;
    @FXML
    private TextField partMin;
    @FXML
    private RadioButton inHouse;
    @FXML
    private RadioButton outsourced;
    @FXML
    private TextField partMachIDOrCompName;
    @FXML
    private Label inOrOut;
    @FXML
    private Button savePart;
    @FXML
    private Button cancelPart;
    @FXML
    private ToggleGroup inHouseOrOutsource;

    /**
     * A helper function to determine if the part is in house or outsourced.
     * @return true if the part is in house, false if it is outsourced.
     */
    public boolean isInHouse() {
        return inHouse.isSelected();
    }
    /**
     * Event handler for the in-house or outsourced toggle field. Changes the label
     * text for the part's machine ID or company name accordingly.
     * @param event the event that triggered the method.
     */
    @FXML
    void toggleField(ActionEvent event) {
        if (isInHouse()) {
            inOrOut.setText("Machine ID");
        } else {
            inOrOut.setText("Company Name");
        }
    }

    /**
     * Event handler for the cancel button. Displays an OK/Cancel dialog box
     * to confirm that the user wishes to cancel adding a new part. If the user clicks
     * OK, the user is taken back to the main screen.
     * @param event the event that triggered the method.
     * @throws IOException if an I/O error occurs while loading the FXML file.
     */
    @FXML
    void cancelPart(ActionEvent event) throws IOException {
        if (InventoryManagementController.OKDialogue("Cancel Adding Part?", "Are you sure you want to cancel?")) {
            Parent addPartParent = FXMLLoader.load(getClass().getResource("InventoryManagementSystem.fxml"));
            Scene addPartScene = new Scene(addPartParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(addPartScene);
            window.show();
        }
    }
    /**
     * Event handler for the save button. Checks if the entered part information is valid,
     * creates a new part object based on the input, and adds it to the inventory. If the
     * input is invalid, displays an error message to the user. If the user clicks Save,
     * the user is taken back to the main screen.
     * @param event the event that triggered the method.
     * @throws IOException if an I/O error occurs while loading the FXML file.
     */
    @FXML
    void savePart(ActionEvent event) throws IOException {
        if (InventoryManagementController.OKDialogue("Save Part",
                "Are you sure you want to save this part as you have entered it?")) {
            try {
                int id = Integer.parseInt(partID.getText());
                String name = partName.getText();
                if(name.equals("")){
                    InventoryManagementController.ErrorDialogue("Error", "You must enter a name");
                    return;
                }
                double price = Double.parseDouble(partPrice.getText());
                int min = Integer.parseInt(partMin.getText());
                // check if the min is less than 0
                if (min < 0) {
                    InventoryManagementController.ErrorDialogue("Error", "Min must be greater than 0");
                    return;
                }
                int max = Integer.parseInt(partMax.getText());
                // check if max is greater than min
                if (max < min) {
                    InventoryManagementController.ErrorDialogue("Error", "Max must be greater than min");
                    return;
                }
                int inv = Integer.parseInt(partInv.getText());
                // check if the inventory is within the min and max
                if (inv < min || inv > max) {
                    InventoryManagementController.ErrorDialogue("Error", "Inventory must be between min and max");
                    return;
                }
                Part newPart;
                if (isInHouse()) {
                    int machineID = Integer.parseInt(partMachIDOrCompName.getText());
                    newPart = new InHousePart(id, name, price, inv, min, max, machineID);
                } else {
                    String companyName = partMachIDOrCompName.getText();
                    newPart = new OutsourcedPart(id, name, price, inv, min, max, companyName);
                }
                Inventory.addPart(newPart);

                // transition back to the main view
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                // load up Main FXML document
                Parent root = FXMLLoader.load(getClass().getResource("InventoryManagementSystem.fxml"));
                // create a new scene with root and set the stage
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();

            } catch (NumberFormatException e) {
                InventoryManagementController.ErrorDialogue("Invalid Input", "Please enter valid input in all fields");
            }
        }
    }

    /**
     * Initializes the Add Part form so that the part ID is set to the next available ID
     * and the part ID text field is disabled.
     */
    public void initialize() {
        partID.setText(Integer.toString(InventoryManagementController.getNextPartId()));
        partID.setDisable(true);
    }
}
