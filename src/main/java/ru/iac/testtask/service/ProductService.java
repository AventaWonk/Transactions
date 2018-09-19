package ru.iac.testtask.service;

import ru.iac.testtask.model.Product;

import java.util.List;

public interface ProductService {

    int getProductCount();

    List<Product> getAllProducts(int offset, int limit);

    List<Product> searchByName(String searchName, int offset, int limit);

    /**
     * Gets an existing product by id
     *
     * @param id id of target product
     * @return   an existing product
     */
    Product getProductById(int id);

    /**
     * Adds a new product
     *
     * @param product a new product
     */
    void addProduct(Product product);

    /**
     * Updates an existing product
     *
     * @param product product with updated fields (excepts id)
     */
    void updateProduct(Product product);


    /**
     * Deletes an existing product from database
     *
     * @param id removing product id
     */
    void deleteProduct(int id);
}
