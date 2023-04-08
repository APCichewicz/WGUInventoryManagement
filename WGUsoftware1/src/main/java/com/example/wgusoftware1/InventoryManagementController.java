package com.example.wgusoftware1;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Optional;

/**

 This class is the controller for the Inventory Management System GUI. It handles all the user

 input and data interactions between the GUI and the Inventory class.
 */
public class InventoryManagementController {

    @FXML
    public TableColumn<Part, Integer> partIDCol;

    @FXML
    private TableView<Part> partTableView;

    @FXML
    private TextField partSearch;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Integer> partInventoryCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Product> productsTableView;

    @FXML
    private TextField prodSearch;

    @FXML
    private TableColumn<Product, Integer> prodIDCol;

    @FXML
    private TableColumn<Product, String> prodNameCol;

    @FXML
    private TableColumn<Product, Integer> prodInventoryCol;

    @FXML
    private TableColumn<Product, Double> prodPriceCol;

    @FXML
    private Button addPart;

    @FXML
    private Button modPart;

    @FXML
    private Button delPart;

    @FXML
    private Button addProd;

    @FXML
    private Button modProd;

    @FXML
    private Button delProd;

    @FXML
    private Button exit;

    private static int nextPartId = 0;

    private static int nextProdId = 0;

    /**

     Changes the scene to a new FXML file, passing optional data as parameters.

     @param FXMLdoc The FXML file to load.

     @param event The event that triggered the scene change.

     @param prod Optional data to pass to the new scene.

     @param part Optional data to pass to the new scene.

     @throws IOException If an error occurs while loading the new FXML file.
     */
    private void changeScene(String FXMLdoc, ActionEvent event, Product prod, Part part) throws IOException {
        // get a reference to the stage
        // do this by getting the source of the event and casting it to a node
        // then get the scene from the node and get the window from the scene
        // then cast the window to a stage
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        // load up OTHER FXML document
        FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLdoc));
        Parent root = loader.load();
        // if the part parameter is not null, do setup for the modpart controller
        if(part != null){
            ModPartController mpc = loader.getController();
            mpc.setPart(part, Inventory.getAllParts().indexOf(part));
        }
        if(prod != null){
            ProductController pc = loader.getController();
            pc.setup(prod);
        }
        // create a new scene with root and set the stage
        Scene scene = new Scene(root);
        //set it so that if the stage is closed from the secondary scene it loads back into the main scene
        stage.setOnCloseRequest(e -> {
            try {
                Parent par = FXMLLoader.load(getClass().getResource("InventoryManagementSystem.fxml"));
                Scene sc = new Scene(par);
                Stage window = (Stage) e.getSource();
                window.setScene(sc);
                window.show();
                e.consume();
                stage.setOnCloseRequest(ev ->{
                    window.close();
                });
            }
            catch (IOException exception){
                InventoryManagementController.ErrorDialogue("Error", exception.getMessage());
            }

        });
        stage.setScene(scene);
        stage.show();
    }
    /**

     Finds a part in the Inventory by its ID and updates the table view to only display that part.
     @param id The ID of the part to be found.
     */
    private void findPartByID(int id) {
        Part found = Inventory.lookupPart(id);
        if (found != null) {
            ObservableList<Part> foundList = FXCollections.observableArrayList();
            foundList.add(found);
            partTableView.setItems(foundList);
        }
    }
    /**

     Finds parts in the Inventory by name and updates the table view to display those parts.
     @param name The name of the part to be found.
     */
    private void findPartByName(String name) {
        ObservableList<Part> foundList = Inventory.lookupPart(name);
        if (foundList != null) {
            partTableView.setItems(foundList);
        }
    }
    /**

     A private helper method to find a product in the inventory by ID and display it in the product table view.
     @param id the ID of the product to be searched for
     */
    private void findProductByID(int id) {
        Product found = Inventory.lookupProduct(id);
        if (found != null) {
            ObservableList<Product> foundList = FXCollections.observableArrayList();
            foundList.add(found);
            productsTableView.setItems(foundList);
        }
    }
    /**
     * Finds and displays a list of products in the Inventory by their name in the productsTableView.
     * If no products are found, the table remains unchanged. The search is not case-sensitive.
     */
    private void findProductByName(String name) {
        ObservableList<Product> foundList = Inventory.lookupProduct(name);
        if (foundList != null) {
            productsTableView.setItems(foundList);
        }
    }
    /**
     This method is called when the user searches Parts tab. It retrieves the text from the search
     text box and uses a regular expression to determine if the user is searching for a part by ID number or by name. If the
     search text is empty, it sets the partsTableView to display all parts in the inventory. If the search text is a number,
     it calls the findPartByID method to search for a part by ID number. Otherwise, it calls the findPartByName method to
     search for a part by name.
     @param event the event object generated by the user clicking the "Search" button
     */
    @FXML
    private void partSearch(ActionEvent event) {
        // regex to check the content of the search textbox for a number
        String text = partSearch.getText();
        if (text.equals("")) {
            partTableView.setItems(Inventory.getAllParts());
        } else if (text.matches("[0-9]+")) {
            findPartByID(Integer.parseInt(text));
        } else {
            findPartByName(text);
        }
    }
    /**
     This method is called when the user searches in the products table view.
     It checks if the search box is empty, and if it is, it sets the items in the table view to display all the products.
     If the search box contains a number, it calls the findProductByID method with the integer value of the text in the search box.
     If the search box contains text, it calls the findProductByName method with the text in the search box as a parameter.
     @param event the action event triggered by the user clicking on the search button
     */
    @FXML
    private void prodSearch(ActionEvent event) {
        // regex to check the content of the search textbox for a number
        String text = prodSearch.getText();
        if (text.equals("")) {
            productsTableView.setItems(Inventory.getAllProducts());
        } else if (text.matches("[0-9]+")) {
            findProductByID(Integer.parseInt(text));
        } else {
            findProductByName(text);
        }
    }

    /**

     This method handles the event triggered when the "Add Part" button is clicked. It calls the changeScene method
     to change the current scene to the Add Part screen.
     @param event The ActionEvent triggered by the "Add Part" button click
     */
    @FXML
    void addPart(ActionEvent event) {
        try {
            changeScene("AddPart.fxml", event, null, null);
        } catch (IOException e){
            InventoryManagementController.ErrorDialogue("Error", e.getMessage());
         }
    }
    /**

     This method handles the event triggered when the "Modify Part" button is clicked. It calls the changeScene method
     to change the current scene to the Modify Part screen.
     @param event The ActionEvent triggered by the "Modify Part" button click
     @throws IOException If there is an error changing to the Modify Part screen
     */
    @FXML
    void modPart(ActionEvent event) throws IOException {
        try {
            changeScene("ModPart.fxml", event, null, this.partTableView.getSelectionModel().getSelectedItem());
        } catch (IOException e){
            InventoryManagementController.ErrorDialogue("Error", e.getMessage());
        }
    }
    /**
     This method is an event handler for the "delete" button for parts.
     it accesses the inventory object and searches the observable list for it
     if found, it checks products to see if any are dependent on it and throws an error if they are
     if not it will remove this part from the list
     @param event The event triggered by the "delete" button click.
     */
    @FXML
    void delPart(ActionEvent event) {
        Part selected = partTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        // check if the part is associated with a product and if it is then don't delete
        // it, throwing an error with the error dialogue
        // this would be easier with a hashmap storage system instead of an observable list. unsure
        // of the actual performance ramifications there. but as it stands this is O(M*N) as for each product
        // every associated part must be iterated through. and that seems inefficient, especially at higher
        // part/product counts
        for (Product prod : Inventory.getAllProducts()) {
            for(Part part : prod.getAllAssociatedParts())
                if(part.getId() == selected.getId()){
                InventoryManagementController.ErrorDialogue("Part is associated with a product",
                        "This part is associated with a product and cannot be deleted.");
                return;
            }
        }
        if (InventoryManagementController.OKDialogue("Delete Part?", "Are you sure you want to delete this part?")) {
            Inventory.deletePart(selected);
        }

    }
    /**
     * Deletes the selected product from the inventory, after confirming with the user.
     * @param event The event that triggered this method.
     */
    @FXML
    void delProd(ActionEvent event) {
        Product selected = productsTableView.getSelectionModel().getSelectedItem();
        if (selected == null) {
            return;
        }
        if (InventoryManagementController.OKDialogue("Delete Product?",
                "Are you sure you want to delete this product?")) {
            Inventory.deleteProduct(selected);
        }
    }

    /**
     Event handler for the "Add Product" button.
     @param event The event that triggered this method call.
     @throws IOException If an error occurs while loading the "Product" FXML file.
     */
    @FXML
    void addProd(ActionEvent event) throws IOException {
        try {
            changeScene("Product.fxml", event, null, null);
        } catch (IOException e){
            InventoryManagementController.ErrorDialogue("Error", e.getMessage());
        }
    }
    /**
     Event handler for the "Modify Product" button.
     @param event The event that triggered this method call.
     @throws IOException If an error occurs while loading the "Product" FXML file.
     */
    @FXML
    void modProd(ActionEvent event) throws IOException {
        try {
            changeScene("Product.fxml", event, this.productsTableView.getSelectionModel().getSelectedItem(), null);
        } catch (IOException e){
            InventoryManagementController.ErrorDialogue("Error", e.getMessage());
        }
    }

    /**
     * Displays a confirmation dialog with a custom title and message.
     *
     * @param ActionTitle the title of the dialog box
     * @param ActionMessage the message to display in the dialog box
     * @return true if the user clicked the "OK" button, false otherwise
     */
    public static Boolean OKDialogue(String ActionTitle, String ActionMessage) {
        // create the alert object
        Alert popup = new Alert(AlertType.CONFIRMATION);
        // set the title and message
        popup.setTitle(ActionTitle);
        popup.setContentText(ActionMessage);
        // show the alert and wait for the user to click a button
        Optional<ButtonType> result = popup.showAndWait();
        // if the user clicks ok return true
        if (result.get() == ButtonType.OK) {
            return true;
        } else {
            // else return false
            return false;
        }
    }
    /**
     * Displays an error dialog with a custom title and message.
     * @param ActionTitle the title of the dialog box
     * @param ActionMessage the message to display in the dialog box
     */
    public static void ErrorDialogue(String ActionTitle, String ActionMessage) {
        // create the alert object
        Alert popup = new Alert(AlertType.ERROR);
        // set the title and message
        popup.setTitle(ActionTitle);
        popup.setContentText(ActionMessage);
        // show the alert and wait for the user to click a button
        popup.showAndWait();
    }
    /**
     * statically retrieves the next valid part id.
     * this was implemented because initially part and prod id's were generated by getting the
     * inventory collection length for either product or part and adding 1.
     * however on reflection deleting a part and adding a new one may result in duplicate id's
     * thus i separated that function
     * @return returns the current value of nextPartId
     */
    public static int getNextPartId(){
        InventoryManagementController.nextPartId +=1;
        int temp = InventoryManagementController.nextPartId;
        return temp;
    }
    /**
     * statically retrieves the next valid part id.
     * this was implemented because initially part and prod id's were generated by getting the
     * inventory collection length for either product or part and adding 1.
     * however on reflection deleting a part and adding a new one may result in duplicate id's
     * thus i separated that function
     * @return returns the current value of nextProdId
     */
    public static int getNextProdId(){
        InventoryManagementController.nextProdId +=1;
        int temp = InventoryManagementController.nextProdId;
        return temp;
    }
    @FXML
    void exit(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

    /**

     Initializes the controller class. This method is automatically called
     after the fxml file has been loaded.
     Initializes the parts and products tables by setting the items and cell
     value factories for each column in the table.
     */
    public void initialize() {
        // Initialize parts and products tables
        partTableView.setItems(Inventory.getAllParts());
        partIDCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        partNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        partInventoryCol
                .setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        partPriceCol
                .setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());

        prodIDCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        prodNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        prodInventoryCol
                .setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStock()).asObject());
        prodPriceCol
                .setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getPrice()).asObject());
        productsTableView.setItems(Inventory.getAllProducts());
    }
}
