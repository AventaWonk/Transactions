package ru.iac.testtask.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.iac.testtask.dao.ShopDao;
import ru.iac.testtask.model.Product;
import ru.iac.testtask.model.Shop;

import java.util.List;

@Repository
public class ShopDaoImpl implements ShopDao {

    private SessionFactory sessionFactory;

    @Override
    public int getShopCount() {
        final Session session = this.sessionFactory.getCurrentSession();
        long count = (Long) session.createQuery("SELECT count(*) FROM Shop")
                .uniqueResult();

        return (int) count;
    }

    @Override
    public List<Shop> findAll(int offset, int limit) {
        final Session session = this.sessionFactory.getCurrentSession();

        return session
                .createQuery("from Shop", Shop.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Shop> searchByAddress(String searchAddress, int offset, int limit) {
        final String selectLikeHql = "SELECT s FROM Shop s WHERE lower(s.address) LIKE lower(:searchAddress)";
        final Session session = this.sessionFactory.getCurrentSession();

        return session.createQuery(selectLikeHql, Shop.class)
                .setParameter("searchAddress", "%"+ searchAddress +"%")
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    /**
     * Returns an existing shop by its primary key (id)
     *
     * @param id id of target shop
     * @return an existing shop
     */
    @Override
    public Shop findById(int id) {
        final Session session = this.sessionFactory.getCurrentSession();

        return session.get(Shop.class, id);
    }

    /**
     * Saves a new shop entity into database and returns its generated
     * primary key (id)
     *
     * @param shop a new shop entity
     */
    @Override
    public void create(Shop shop) {
        final Session session = this.sessionFactory.getCurrentSession();
        session.save(shop);
    }

    /**
     * Updates an existing in database shop entity
     *
     * @param shop an existing shop entity with updated fields (excepts id)
     */
    @Override
    public void update(Shop shop) {
        final Session session = this.sessionFactory.getCurrentSession();
        session.update(shop);
    }

    @Override
    public void remove(int id) {
        final String deleteHql = "DELETE FROM Shop WHERE id = :id";
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
