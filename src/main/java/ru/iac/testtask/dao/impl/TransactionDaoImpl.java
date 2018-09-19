package ru.iac.testtask.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.iac.testtask.dao.TransactionDao;
import ru.iac.testtask.dto.TransactionGroupDto;
import ru.iac.testtask.model.Transaction;
import ru.iac.testtask.util.OrderByStatementUtil;
import ru.iac.testtask.util.SqlUtil;

import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

@Repository
public class TransactionDaoImpl implements TransactionDao {

    private SessionFactory sessionFactory;

    @Override
    public int getTransactionCount() {
        this.getTransactionCountWithGroupByProduct();
        final Session session = this.sessionFactory.getCurrentSession();
        long count = (Long) session.createQuery("SELECT count(*) FROM Store_transaction")
                .uniqueResult();

        return (int) count;
    }

    @Override
    public int getTransactionCountByProductId(int productId) {
        final String selectHql = "SELECT count(s) FROM Store_transaction s WHERE s.product.id = :productId";
        final Session session = this.sessionFactory.getCurrentSession();
        long count = (Long) session.createQuery(selectHql)
                .setParameter("productId", productId)
                .uniqueResult();

        return (int) count;
    }

    @Override
    public int getTransactionCountByProductIdAndPeriod(int productId, Date start, Date end) {
        final String selectHql = "SELECT count(s) FROM Store_transaction s WHERE s.product.id = :productId AND " +
                "s.date BETWEEN :start AND :end";
        final Session session = this.sessionFactory.getCurrentSession();
        long count = (Long) session.createQuery(selectHql)
                .setParameter("productId", productId)
                .setParameter("start", start, TemporalType.TIMESTAMP)
                .setParameter("end", end, TemporalType.TIMESTAMP)
                .uniqueResult();

        return (int) count;
    }

    @Override
    public int getTransactionCountByPeriod(Date start, Date end) {
        final String selectHql = "SELECT count(s) FROM Store_transaction s WHERE s.date BETWEEN :start AND :end";
        final Session session = this.sessionFactory.getCurrentSession();
        long count = (Long) session.createQuery(selectHql)
                .setParameter("start", start, TemporalType.TIMESTAMP)
                .setParameter("end", end, TemporalType.TIMESTAMP)
                .uniqueResult();

        return (int) count;
    }

    @Override
    public int getTransactionCountWithGroupByProduct() {
        final String selectSql = "SELECT count(*) FROM ( SELECT count(*) from store_transaction GROUP BY shop_id)" +
                " AS CNT";
        final Session session = this.sessionFactory.getCurrentSession();
        BigInteger count = (BigInteger) session.createNativeQuery(selectSql)
                .uniqueResult();

        return count.intValue();
    }

    @Override
    public int getTransactionCountWithGroupByAmount() {
        final String selectSql = "SELECT count(*) FROM ( SELECT count(*) from store_transaction GROUP BY amount)" +
                " AS CNT";
        final Session session = this.sessionFactory.getCurrentSession();
        BigInteger count = (BigInteger) session.createNativeQuery(selectSql)
                .uniqueResult();

        return count.intValue();
    }

    @Override
    public int getTransactionCountWithGroupByQuantity() {
        final String selectSql = "SELECT count(*) FROM ( SELECT count(*) from store_transaction GROUP BY quantity)" +
                " AS CNT";
        final Session session = this.sessionFactory.getCurrentSession();
        BigInteger count = (BigInteger) session.createNativeQuery(selectSql)
                .uniqueResult();

        return count.intValue();
    }

    @Override
    public int getTransactionCountWithGroupByDate() {
        final String selectSql = "SELECT count(*) FROM ( SELECT count(*) from store_transaction GROUP BY date)" +
                " AS CNT";
        final Session session = this.sessionFactory.getCurrentSession();
        BigInteger count = (BigInteger) session.createNativeQuery(selectSql)
                .uniqueResult();

        return count.intValue();
    }

    @Override
    public List<Transaction> findAll(int offset, int limit, OrderByStatementUtil order) {
//        test();
        final String selectHql = "SELECT s FROM Store_transaction s" + SqlUtil.getOrderByStatement("s", order);
        final Session session = sessionFactory.getCurrentSession();

        return session.createQuery(selectHql, Transaction.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Transaction> findByProductId(int productId, int offset, int limit, OrderByStatementUtil order) {
        final String selectHql = "SELECT s FROM Store_transaction s WHERE s.product.id = :productId" +
                SqlUtil.getOrderByStatement("s", order);
        final Session session = sessionFactory.getCurrentSession();

        return session.createQuery(selectHql, Transaction.class)
                .setParameter("productId", productId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Transaction> findByProductIdAndPeriod(int productId, Date start, Date end, int offset, int limit, OrderByStatementUtil order) {
        final String selectHql = "SELECT s FROM Store_transaction s WHERE s.product.id = :productId AND " +
                "s.date BETWEEN :start AND :end" + SqlUtil.getOrderByStatement("s", order);
        final Session session = sessionFactory.getCurrentSession();

        return session.createQuery(selectHql, Transaction.class)
                .setParameter("productId", productId)
                .setParameter("start", start, TemporalType.TIMESTAMP)
                .setParameter("end", end,  TemporalType.TIMESTAMP)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Transaction> findByPeriod(Date start, Date end, int offset, int limit, OrderByStatementUtil order) {
        final String selectHql = "SELECT s FROM Store_transaction s WHERE s.date BETWEEN :start AND :end"
                + SqlUtil.getOrderByStatement("s", order);
        final Session session = sessionFactory.getCurrentSession();

        return session.createQuery(selectHql, Transaction.class)
                .setParameter("start", start, TemporalType.TIMESTAMP)
                .setParameter("end", end,  TemporalType.TIMESTAMP)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<TransactionGroupDto> getGroupsByProduct(int offset, int limit, OrderByStatementUtil order) {
        final String groupByHql = "SELECT new ru.iac.testtask.dto.TransactionGroupDto(COUNT(s.product.id) as quantity, " +
                "s.product.id, SUM(s.amount) as amount, MAX(s.date) as date) FROM Store_transaction s GROUP BY s.product.id" +
                SqlUtil.getOrderByStatement("", order);
        final Session session = sessionFactory.getCurrentSession();
        return session.createQuery(groupByHql, TransactionGroupDto.class)
                .getResultList();
    }

    @Override
    public List<TransactionGroupDto> getGroupsByAmount(int offset, int limit, OrderByStatementUtil order) {
        final String groupByHql = "SELECT new ru.iac.testtask.dto.TransactionGroupDto(COUNT(s.product.id) as quantity, " +
                "s.amount, SUM(s.amount) as amount, MAX(s.date) as date) FROM Store_transaction s GROUP BY s.amount" +
                SqlUtil.getOrderByStatement("", order);
        final Session session = sessionFactory.getCurrentSession();
        return session.createQuery(groupByHql, TransactionGroupDto.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }

    @Override
    public List<TransactionGroupDto> getGroupsByQuantity(int offset, int limit, OrderByStatementUtil order) {
        final String groupByHql = "SELECT new ru.iac.testtask.dto.TransactionGroupDto(COUNT(s.product.id) as quantity, " +
                "s.quantity, SUM(s.amount) as amount, MAX(s.date) as date) FROM Store_transaction s GROUP BY s.quantity" +
                SqlUtil.getOrderByStatement("", order);
        final Session session = sessionFactory.getCurrentSession();
        return session.createQuery(groupByHql, TransactionGroupDto.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }

    @Override
    public List<TransactionGroupDto> getGroupsByDate(int offset, int limit, OrderByStatementUtil order) {
        final String groupByHql = "SELECT new ru.iac.testtask.dto.TransactionGroupDto(COUNT(s.product.id) as quantity, " +
                "s.date, SUM(s.amount) as amount, MAX(s.date) as date) FROM Store_transaction s GROUP BY s.date" +
                SqlUtil.getOrderByStatement("", order);
        final Session session = sessionFactory.getCurrentSession();
        return session.createQuery(groupByHql, TransactionGroupDto.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }

    @Override
    public BigDecimal getSumByProductId(int productId) {
        final String selectSumHql = "SELECT sum(t.amount) FROM Store_transaction t WHERE t.product.id = :productId";
        final Session session = sessionFactory.getCurrentSession();

        return  (BigDecimal) session.createQuery(selectSumHql)
                .setParameter("productId", productId)
                .uniqueResult();
    }

    @Override
    public BigDecimal getSumByProductIdAndPeriod(int productId, Date start, Date end) {
        final String selectSumHql = "SELECT sum(t.amount) FROM Store_transaction t WHERE t.product.id = :productId AND " +
                "t.date BETWEEN :start AND :end";
        final Session session = sessionFactory.getCurrentSession();

        return  (BigDecimal) session.createQuery(selectSumHql)
                .setParameter("productId", productId)
                .setParameter("start", start)
                .setParameter("end", end)
                .uniqueResult();
    }

    @Override
    public BigDecimal getSumByPeriod(Date start, Date end) {
        final String selectSumHql = "SELECT sum(t.amount) FROM Store_transaction t WHERE t.date BETWEEN :start AND :end";
        final Session session = sessionFactory.getCurrentSession();

        return  (BigDecimal) session.createQuery(selectSumHql)
                .setParameter("start", start)
                .setParameter("end", end)
                .uniqueResult();
    }

    @Override
    public Transaction findById(int id) {
        final Session session = this.sessionFactory.getCurrentSession();

        return session.get(Transaction.class, id);
    }

    @Override
    public void create(Transaction transaction) {
        final Session session = this.sessionFactory.getCurrentSession();
        session.save(transaction);
    }

    @Override
    public void update(Transaction transaction) {
        final Session session = this.sessionFactory.getCurrentSession();
        session.update(transaction);
    }

    @Override
    public void remove(int id) {
        final String deleteHql = "DELETE FROM Store_transaction s WHERE s.id = :id";
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
