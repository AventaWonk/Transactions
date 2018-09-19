package ru.iac.testtask.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.iac.testtask.dao.ProductDao;
import ru.iac.testtask.model.Product;

import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao {

    private SessionFactory sessionFactory;

    /**
     * Returns number of persisted in database product entities
     *
     * @return number of all products in database
     */
    @Override
    public int getProductCount() {
        final Session session = this.sessionFactory.getCurrentSession();
        long count = (Long) session.createQuery("SELECT count(*) FROM Product")
                .uniqueResult();

        return (int) count;
    }

    /**
     * Returns all persisted in database Products by offset and limit
     *
     * @param offset minimum position of Products in db
     * @param limit maximum number of returning Products
     * @return List of all products in database by offces an limit
     */
    @Override
    public List<Product> findAll(int offset, int limit) {
        final Session session = this.sessionFactory.getCurrentSession();

        return session
                .createQuery("FROM Product", Product.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * Searches for Products in the db via the searchName
     *
     * @param searchName name of searching Product
     * @param offset minimum position of Products in db
     * @param limit maximum number of returning Products
     * @return List of suited products
     */
    @Override
    public List<Product> searchByName(String searchName, int offset, int limit) {
        final String selectLikeHql = "SELECT p FROM Product p WHERE lower(p.name) LIKE lower(:searchName)";
        final Session session = this.sessionFactory.getCurrentSession();

        return session.createQuery(selectLikeHql, Product.class)
                .setParameter("searchName", "%"+ searchName +"%")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * Returns an existing product by primary key (id)
     *
     * @param id id of target product
     * @return   an existing product
     */
    @Override
    public Product findById(int id) {
        final Session session = this.sessionFactory.getCurrentSession();

        return session.get(Product.class, id);
    }

    @Override
    public void create(Product product) {
        final Session session = this.sessionFactory.getCurrentSession();
        session.save(product);
    }

    @Override
    public void update(Product product) {
        final Session session = this.sessionFactory.getCurrentSession();
        session.update(product);
    }

    @Override
    public void remove(int id) {
        final String deleteHql = "DELETE FROM Product WHERE id = :id";
        final Session session = this.sessionFactory.getCurrentSession();

        session.createQuery(deleteHql)
                .setParameter("id", id)
                .executeUpdate();
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
