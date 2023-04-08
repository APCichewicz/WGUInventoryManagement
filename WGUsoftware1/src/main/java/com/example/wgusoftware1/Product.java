package com.example.wgusoftware1;

import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
/**
 * class Product, a class to serve the purpose of representing product objects
 */
class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    /**
     Constructs a Product object with the given id, name, price, stock, min, and max.
     @param id the id of the product
     @param name the name of the product
     @param price the price of the product
     @param stock the stock of the product
     @param min the minimum stock of the product
     @param max the maximum stock of the product
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
        this.associatedParts = FXCollections.observableArrayList();
    }
    /**

     Sets the id of the product to the given id.
     @param id the new id of the product
     */
    public void setId(int id) {
        this.id = id;
    }
    /**

     Sets the name of the product to the given name.
     @param name the new name of the product
     */
    public void setName(String name) {
        this.name = name;
    }
    /**

     Sets the price of the product to the given price.
     @param price the new price of the product
     */
    public void setPrice(double price) {
        this.price = price;
    }
    /**

     Sets the stock of the product to the given stock.
     @param stock the new stock of the product
     */
    public void setStock(int stock) {
        this.stock = stock;
    }
    /**

     Sets the minimum stock of the product to the given minimum.
     @param min the new minimum stock of the product
     */
    public void setMin(int min) {
        this.min = min;
    }
    /**

     Sets the maximum stock of the product to the given maximum.
     @param max the new maximum stock of the product
     */
    public void setMax(int max) {
        this.max = max;
    }
    /**

     Returns the id of the product.
     @return the id of the product
     */
    public int getId() {
        return id;
    }
    /**

     Returns the name of the product.
     @return the name of the product
     */
    public String getName() {
        return name;
    }

    /**

     Returns the price of the product.
     @return the price of the product
     */
    public double getPrice() {
        return price;
    }

    /**

     Returns the stock of the product.
     @return the stock of the product
     */
    public int getStock() {
        return stock;
    }

    /**

     Returns the minimum stock of the product.
     @return the minimum stock of the product
     */
    public int getMin() {
        return min;
    }

    /**

     Returns the maximum stock of the product.
     @return the maximum stock of the product
     */
    public int getMax() {
        return max;
    }

    /**

     Adds the given part to the list of associated parts for the product.
     @param part the part to add to the associated parts list
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }
    /**

     Removes a part from the list of associated parts.
     @param selectedAssociatedPart the part to remove
     @return true if the part was removed successfully, false otherwise
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**

     Returns an observable list of all associated parts.
     @return an observable list of all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
