package ru.iac.testtask.dao;

import ru.iac.testtask.model.Product;

import java.util.List;

public interface ProductDao {

    /**
     * Returns number of persisted in database product entities
     *
     * @return number of all products in database
     */
    int getProductCount();

    /**
     * Returns all persisted in database Products by offset and limit
     *
     * @param offset minimum position of Products in db
     * @param limit maximum number of returning Products
     * @return List of all products in database by offces an limit
     */
    List<Product> findAll(int offset, int limit);

    /**
     * Searches for Products in the db via searchName
     *
     * @param searchName name of searching Product
     * @param offset minimum position of Products in db
     * @param limit maximum number of returning Products
     * @return List of suited products
     */
    List<Product> searchByName(String searchName, int offset, int limit);

    Product findById(int id);

    void create(Product product);

    void update(Product product);

    void remove(int id);
}
