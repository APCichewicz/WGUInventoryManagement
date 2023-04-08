package com.example.wgusoftware1;

import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The `Inventory` class manages the collection of all parts and products in the inventory management system.
 */
public class Inventory {

    /**
     * A collection of all parts in the inventory.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * A collection of all products in the inventory.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Adds a new part to the inventory.
     *
     * @param newPart The part to add to the inventory.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a new product to the inventory.
     *
     * @param newProduct The product to add to the inventory.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Searches for a part in the inventory by its ID.
     *
     * @param partId The ID of the part to search for.
     * @return The part with the given ID if found, or `null` if not found.
     */
    public static Part lookupPart(int partId) {
        Optional<Part> foundPart = allParts.stream().filter(p -> p.getId() == partId).findFirst();
        if (foundPart.isPresent()) {
            return foundPart.get();
        }
        return null;
    }

    /**
     * Searches for a product in the inventory by its ID.
     *
     * @param productId The ID of the product to search for.
     * @return The product with the given ID if found, or `null` if not found.
     */
    public static Product lookupProduct(int productId) {
        Optional<Product> foundProduct = allProducts.stream().filter(p -> p.getId() == productId).findFirst();
        if (foundProduct.isPresent()) {
            return foundProduct.get();
        }
        return null;
    }

    /**
     * Searches for parts in the inventory whose names match the given name.
     *
     * @param partName The name of the part(s) to search for.
     * @return An observable list of parts whose names match the given name.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        return allParts.filtered(part -> part.getName().equalsIgnoreCase(partName));
    }

    /**
     * Searches for products in the inventory whose names match the given name.
     *
     * @param productName The name of the product(s) to search for.
     * @return An observable list of products whose names match the given name.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        return allProducts.filtered(product -> product.getName().equalsIgnoreCase(productName));
    }

    /**
     * Updates a part in the inventory at the specified index with a new part.
     *
     * @param index        The index of the part to update.
     * @param selectedPart The new part to replace the old part with.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates a product in the inventory at the specified index with a new product.
     *
     * @param index      The index of the product to update.
     * @param newProduct The new product to replace the old product with.
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes a Part from the inventory.
     *
     * @param selectedPart the Part to be deleted
     * @return true if the Part was deleted, false otherwise
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes a Product from the inventory.
     *
     * @param selectedProduct the Product to be deleted
     * @return true if the Product was deleted, false otherwise
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Returns an ObservableList containing all Parts in the inventory.
     *
     * @return an ObservableList containing all Parts in the inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Returns an ObservableList containing all Products in the inventory.
     *
     * @return an ObservableList containing all Products in the inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}