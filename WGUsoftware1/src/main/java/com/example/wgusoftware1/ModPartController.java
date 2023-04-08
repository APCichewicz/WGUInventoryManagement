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

 The ModPartController class is the controller for the Modify Part screen in the Inventory

 Management System. This class handles user input and updates the inventory accordingly.
 */
public class ModPartController {
    private Part selected;
    private int index;
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
     Sets the selected part and its index to modify.
     @param selected the selected part to modify.
     @param index the index of the selected part to modify.
     */
    public void setPart(Part selected, int index) {
        this.index = index;
        this.selected = selected;
        // set the text fields to the selected part
        partID.setText(Integer.toString(selected.getId()));
        partName.setText(selected.getName());
        partInv.setText(Integer.toString(selected.getStock()));
        partPrice.setText(Double.toString(selected.getPrice()));
        partMax.setText(Integer.toString(selected.getMax()));
        partMin.setText(Integer.toString(selected.getMin()));
        // check if the part is in house or outsourced
        if (selected instanceof InHousePart) {
            inHouse.setSelected(true);
            inOrOut.setText("Machine ID");
            partMachIDOrCompName.setText(Integer.toString(((InHousePart) selected).getMachineId()));
        } else {
            outsourced.setSelected(true);
            inOrOut.setText("Company Name");
            partMachIDOrCompName.setText(((OutsourcedPart) selected).getCompanyName());
        }
        partID.setDisable(true);
    }

    /**
     A helper function to determine if the part is in house or outsourced.
     @return true if the part is in house, false if it is outsourced.
     */
    public boolean isInHouse() {
        return inHouse.isSelected();
    }

    /**
     Event handler for the in-house or outsourced toggle field. Changes the label
     text for the part's machine ID or company name accordingly.
     @param event the event that triggered the method.
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
     Event handler for the cancel button. Displays an OK/Cancel dialog box
     to confirm that the user wishes to cancel modifying a part. If the user clicks
     OK, the user is taken back to the main screen.
     @param event the event that triggered the method.
     @throws IOException if an I/O error occurs while loading the FXML file.
     */
    @FXML
    void cancelPart(ActionEvent event) throws IOException {
        if (InventoryManagementController.OKDialogue("Cancel Modifying Part?", "Are you sure you want to cancel?")) {
            Parent addPartParent = FXMLLoader.load(getClass().getResource("InventoryManagementSystem.fxml"));
            Scene addPartScene = new Scene(addPartParent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(addPartScene);
            window.show();
        }
    }
    /**

     Saves the edited part to the allParts list in the Inventory class and transitions back to the main view.

     Validates the input before saving.

     If the input is invalid, an error message is displayed.

     If the user confirms to save the part, a new Part object is created using the input fields.

     If the part is in-house, an InHousePart object is created, else an OutsourcedPart object is created.

     The new Part object is then updated in the allParts list in the Inventory class using the updatePart() method.

     The user is then returned to the main view.

     @param event the event that triggered this method

     @throws IOException if an I/O exception occurs
     */
    @FXML
    void savePart(ActionEvent event) throws IOException {
        if (InventoryManagementController.OKDialogue("Save Part",
                "Are you sure you want to save this part as you have entered it?")) {
            try {
                int id = Integer.parseInt(partID.getText());
                String name = partName.getText();
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
                // instead of simply creating a new part and adding it to the allParts list in
                // the Inventory class
                // we will instead replace the part in the allParts list with the new part
                Part newPart;
                if (isInHouse()) {
                    int machineID = Integer.parseInt(partMachIDOrCompName.getText());
                    newPart = new InHousePart(id, name, price, inv, min, max, machineID);
                } else {
                    String companyName = partMachIDOrCompName.getText();
                    newPart = new OutsourcedPart(id, name, price, inv, min, max, companyName);
                }
                Inventory.updatePart(this.index, newPart);

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
}
