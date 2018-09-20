package ru.iac.testtask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.iac.testtask.dao.ProductDao;
import ru.iac.testtask.model.Product;
import ru.iac.testtask.service.ProductService;

import java.util.List;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductDao productDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public int getProductCount() {
        return this.productDao.getProductCount();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Product> getAllProducts(int offset, int limit) {
        return this.productDao.findAll(offset, limit);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Product> searchByName(String searchName, int offset, int limit) {
        return this.productDao.searchByName(searchName, offset, limit);
    }

    /**
     * Gets an existing product by id
     *
     * @param id id of target product
     * @return an existing product
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Product getProductById(int id) {
        return this.productDao.findById(id);
    }

    /**
     * Adds a new product
     *
     * @param product a new product
     */
    @Override
    public void addProduct(Product product) {
        this.productDao.create(product);
    }

    /**
     * Updates an existing product
     *
     * @param product product with updated fields (excepts id)
     */
    @Override
    public void updateProduct(Product product) {
        this.productDao.update(product);
    }

    @Override
    public void deleteProduct(int productId) {
        this.productDao.remove(productId);
    }

    @Autowired
    public void setProductDao(ProductDao productDao) {
        this.productDao = productDao;
    }
}
