package com.example.wgusoftware1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Iterator;

/**
 * The controller class for the Product view.
 */
public class ProductController {

    private ObservableList<Part> usedParts;
    private Product product;

    // get all scene controls into variables
    @FXML
    private TextField prodID;
    @FXML
    private TextField prodName;
    @FXML
    private TextField prodStock;
    @FXML
    private TextField prodPrice;
    @FXML
    private TextField prodMax;
    @FXML
    private TextField prodMin;
    @FXML
    private TextField partSearch;
    @FXML
    private TableView<Part> partListTableView;
    @FXML
    private TableColumn<Part, Integer> partListID;
    @FXML
    private TableColumn<Part, String> partListName;
    @FXML
    private TableColumn<Part, Integer> partListStock;
    @FXML
    private TableColumn<Part, Double> partListPrice;
    @FXML
    private TableView<Part> usedPartsTableView;
    @FXML
    private TableColumn<Part, Integer> usedPartId;
    @FXML
    private TableColumn<Part, String> usedPartName;
    @FXML
    private TableColumn<Part, Integer> usedPartStock;
    @FXML
    private TableColumn<Part, Double> usedPartPrice;
    @FXML
    private Button addPart;
    @FXML
    private Button removePart;
    @FXML
    private Button save;
    @FXML
    private Button cancel;

    /**
     * The setup method accepts a product and populates the scene with the product's
     * data.
     * 
     * @param product The product to be displayed on the scene.
     */
    public void setup(Product product) {
        this.product = product;
        prodID.setText(Integer.toString(product.getId()));
        prodName.setText(product.getName());
        prodStock.setText(Integer.toString(product.getStock()));
        prodPrice.setText(Double.toString(product.getPrice()));
        prodMax.setText(Integer.toString(product.getMax()));
        prodMin.setText(Integer.toString(product.getMin()));
        partListTableView.setItems(Inventory.getAllParts());
        for (Part part : this.product.getAllAssociatedParts()) {
            this.usedParts.add(part);
        }
        usedPartsTableView.setItems(product.getAllAssociatedParts());
    }

    /**
     * The findPartByID method searches for a part by ID and sets the part list
     * table view to the found part.
     * 
     * @param id The ID of the part to search for.
     */
    private void findPartByID(int id) {
        Part found = Inventory.lookupPart(id);
        if (found != null) {
            ObservableList<Part> foundList = FXCollections.observableArrayList();
            foundList.add(found);
            partListTableView.setItems(foundList);
        }
    }

    /**
     * The findPartByName method searches for a part by name and sets the part list
     * table view to the found part(s).
     * 
     * @param name The name of the part to search for.
     */
    private void findPartByName(String name) {
        ObservableList<Part> foundList = Inventory.lookupPart(name);
        if (foundList != null) {
            partListTableView.setItems(foundList);
        }
    }

    /**
     * 
     * Searches for a part by ID or name and updates the part list table view
     * accordingly.
     */
    @FXML
    void partSearch() {
        // get the search term from the part search text field
        String searchTerm = partSearch.getText();
        // if the search term is empty set the part list table view to the full list of
        // parts
        if (searchTerm.isEmpty()) {
            partListTableView.setItems(Inventory.getAllParts());
        } else {
            if (searchTerm.matches("[0-9]+")) {
                findPartByID(Integer.parseInt(searchTerm));
            } else {
                findPartByName(searchTerm);
            }
        }
    }

    /**
     * 
     * Add the selected part to the list of used parts for the product.
     * If the selected part is already in the list of used parts, display an error
     * message.
     * 
     * @param event the ActionEvent object that triggered the method
     */
    @FXML
    void addPart(ActionEvent event) {
        // get the selected part from the part list table view
        Part part = partListTableView.getSelectionModel().getSelectedItem();
        // if the part is not null add it to the used parts list
        if (part != null) {
            // if the part is not already in the used parts list, add it
            if (!usedParts.contains(part)) {
                usedParts.add(part);
            } else {
                InventoryManagementController.ErrorDialogue("Illegal Addition", "Part already added to product");
            }
        }
        // repopulate the used parts table view
        usedPartsTableView.setItems(usedParts);
    }

    /**
     * Initializes the controller after its root element has been completely
     * processed.
     * This method sets up the table views and columns with the appropriate cell
     * value factories.
     * It also initializes the product ID and the used parts list.
     */
    public void initialize() {
        // create a temporary product to hold the data for the new product, will be
        // replaced if the setup function is called
        this.product = new Product(0, "", 0, 0, 0, 0);
        // set the product id to the next available id
        //if this is overwritten by calling the setup and an id number is "skipped" its ok as
        //the requirements state id's do not need to be contiguous.
        this.product.setId(InventoryManagementController.getNextProdId());
        // set the product id text field to the product id and disable it
        prodID.setText(Integer.toString(this.product.getId()));
        prodID.setDisable(true);
        // initialize an empty observable list for the used parts, will be replaced if a
        // product is provided
        usedParts = FXCollections.observableArrayList();

        partListTableView.setItems(Inventory.getAllParts());
        partListID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        partListName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        partListStock
                .setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        partListPrice
                .setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        usedPartId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        usedPartName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        usedPartStock
                .setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        usedPartPrice
                .setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        // set the partListTableView to the list of all parts
        partListTableView.setItems(Inventory.getAllParts());
    }

    /**
     * 
     * Remove a selected part from the used parts table view and from the list of
     * associated parts for the product being edited/created.
     * Repopulate the used parts table view.
     * 
     * @param event The event that triggered this method.
     */
    public void removePart(ActionEvent event) {
        // get the selected part from the used parts table view
        Part part = usedPartsTableView.getSelectionModel().getSelectedItem();
        // if the part is not null remove it from the used parts list
        if (part != null) {
            usedParts.remove(part);
        }
        // repopulate the associated parts tableview
        usedPartsTableView.setItems(usedParts);
    }

    /**
     * 
     * Cancels adding a new product and returns to the main Inventory Management
     * System
     * screen. Prompts the user to confirm the cancellation before navigating back
     * to
     * the main screen.
     * 
     * @param event the ActionEvent object representing the user's click on the
     *              cancel button
     * @throws IOException if an error occurs during the loading of the FXML file
     *                     for the
     *                     main screen
     */
    public void cancel(ActionEvent event) throws IOException {
        if (InventoryManagementController.OKDialogue("Cancel Adding Part?", "Are you sure you want to cancel?")) {
            Parent parent = FXMLLoader.load(getClass().getResource("InventoryManagementSystem.fxml"));
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }
    }

    /**
     * 
     * Saves the information entered into the AddProduct view and adds or updates
     * the
     * Product in the Inventory.
     * If the required information is missing or invalid, displays an error message.
     * If a Product with the same ID already exists in the Inventory, updates the
     * existing Product.
     * Otherwise, adds the new Product to the Inventory.
     * Upon successful save, navigates back to the main InventoryManagementSystem
     * view.
     * 
     * @param event The event that triggered this method.
     */
    public void save(ActionEvent event) {

        if (InventoryManagementController.OKDialogue("Save?", "Save the entered information?")) {

            if (prodName.getText().isEmpty()) {
                InventoryManagementController.ErrorDialogue("Invalid Name", "Name cannot be empty");
                return;
            }
            if (prodStock.getText().isEmpty()) {
                InventoryManagementController.ErrorDialogue("Invalid Stock", "Stock cannot be empty");
                return;
            }
            if (prodPrice.getText().isEmpty()) {
                InventoryManagementController.ErrorDialogue("Invalid Price", "Price cannot be empty");
                return;
            }
            if (prodMax.getText().isEmpty()) {
                InventoryManagementController.ErrorDialogue("Invalid Max", "Max cannot be empty");
                return;
            }
            if (prodMin.getText().isEmpty()) {
                InventoryManagementController.ErrorDialogue("Invalid Min", "Min cannot be empty");
                return;
            }
            try {
                int id = Integer.parseInt(prodID.getText());
            } catch (NumberFormatException e) {
                InventoryManagementController.ErrorDialogue("Invalid ID", "ID must be a number");
                return;
            }
            try {
                int stock = Integer.parseInt(prodStock.getText());
            } catch (NumberFormatException e) {
                InventoryManagementController.ErrorDialogue("Invalid Stock", "Stock must be a number");
                return;
            }
            try {
                int max = Integer.parseInt(prodMax.getText());
            } catch (NumberFormatException e) {
                InventoryManagementController.ErrorDialogue("Invalid Max", "Max must be a number");
                return;
            }
            try {
                int min = Integer.parseInt(prodMin.getText());
            } catch (NumberFormatException e) {
                InventoryManagementController.ErrorDialogue("Invalid Min", "Min must be a number");
                return;
            }
            try {
                double price = Double.parseDouble(prodPrice.getText());
            } catch (NumberFormatException e) {
                InventoryManagementController.ErrorDialogue("Invalid Price", "Price must be a number");
                return;
            }

            if (Integer.parseInt(prodMax.getText()) < Integer.parseInt(prodMin.getText())) {
                InventoryManagementController.ErrorDialogue("Invalid Min/Max", "Max must be greater than Min");
                return;
            }
            if (Integer.parseInt(prodStock.getText()) > Integer.parseInt(prodMax.getText())) {
                InventoryManagementController.ErrorDialogue("Invalid Stock", "Stock must be less than Max");
                return;
            }
            if (Integer.parseInt(prodStock.getText()) < Integer.parseInt(prodMin.getText())) {
                InventoryManagementController.ErrorDialogue("Invalid Stock", "Stock must be greater than Min");
                return;
            }
            this.product.setName(prodName.getText());
            this.product.setStock(Integer.parseInt(prodStock.getText()));
            this.product.setPrice(Double.parseDouble(prodPrice.getText()));
            this.product.setMax(Integer.parseInt(prodMax.getText()));
            this.product.setMin(Integer.parseInt(prodMin.getText()));
            // for part in usedParts, add part to product
            // this initially threw a concurrentmodificationexcetption, so i changed it to
            // use an iterator
            Iterator<Part> itr = this.usedParts.iterator();
            while (itr.hasNext()) {
                // if the part is not already in the product's associated parts, add it
                // i forgot to check for this initially and it was causing duplicates, couldnt
                // figure out why
                Part temp = itr.next();
                if (!this.product.getAllAssociatedParts().contains(temp)){
                    this.product.addAssociatedPart(temp);
                }
            }

            if (Inventory.lookupProduct(this.product.getId()) != null) {
                Inventory.updateProduct(Inventory.getAllProducts().indexOf(this.product), this.product);
            } else {
                Inventory.addProduct(this.product);
            }
            try {
                Parent addPartParent = FXMLLoader.load(getClass().getResource("InventoryManagementSystem.fxml"));
                Scene main = new Scene(addPartParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(main);
                window.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
