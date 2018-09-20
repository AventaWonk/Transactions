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
        this.getCountOfGroupsByProduct();
        final Session session = this.sessionFactory.getCurrentSession();
        long count = (Long) session.createQuery("SELECT count(t) FROM Store_transaction t")
                .uniqueResult();

        return (int) count;
    }

    @Override
    public int getTransactionCountByProductId(int productId) {
        final String selectHql = "SELECT count(t) FROM Store_transaction t WHERE t.product.id = :productId";
        final Session session = this.sessionFactory.getCurrentSession();
        long count = (Long) session.createQuery(selectHql)
                .setParameter("productId", productId)
                .uniqueResult();

        return (int) count;
    }

    @Override
    public int getTransactionCountByProductIdAndPeriod(int productId, Date start, Date end) {
        final String selectHql = "SELECT count(t) FROM Store_transaction t WHERE t.product.id = :productId AND " +
                "t.date BETWEEN :start AND :end";
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
        final String selectHql = "SELECT count(t) FROM Store_transaction t WHERE t.date BETWEEN :start AND :end";
        final Session session = this.sessionFactory.getCurrentSession();
        long count = (Long) session.createQuery(selectHql)
                .setParameter("start", start, TemporalType.TIMESTAMP)
                .setParameter("end", end, TemporalType.TIMESTAMP)
                .uniqueResult();

        return (int) count;
    }

    @Override
    public int getCountOfGroupsByProduct() {
        final String selectSql = "SELECT count(*) FROM ( SELECT count(id) from store_transaction GROUP BY shop_id)" +
                " AS CNT";
        final Session session = this.sessionFactory.getCurrentSession();
        BigInteger count = (BigInteger) session.createNativeQuery(selectSql)
                .uniqueResult();

        return count.intValue();
    }

    @Override
    public int getCountOfGroupsByAmount() {
        final String selectSql = "SELECT count(*) FROM ( SELECT count(id) from store_transaction GROUP BY amount)" +
                " AS CNT";
        final Session session = this.sessionFactory.getCurrentSession();
        BigInteger count = (BigInteger) session.createNativeQuery(selectSql)
                .uniqueResult();

        return count.intValue();
    }

    @Override
    public int getCountOfGroupByQuantity() {
        final String selectSql = "SELECT count(*) FROM ( SELECT count(id) from store_transaction GROUP BY quantity)" +
                " AS CNT";
        final Session session = this.sessionFactory.getCurrentSession();
        BigInteger count = (BigInteger) session.createNativeQuery(selectSql)
                .uniqueResult();

        return count.intValue();
    }

    @Override
    public int getCountOfGroupByDate() {
        final String selectSql = "SELECT count(*) FROM ( SELECT count(id) from store_transaction GROUP BY date)" +
                " AS CNT";
        final Session session = this.sessionFactory.getCurrentSession();
        BigInteger count = (BigInteger) session.createNativeQuery(selectSql)
                .uniqueResult();

        return count.intValue();
    }

    @Override
    public List<Transaction> findAll(int offset, int limit, OrderByStatementUtil order) {
        final String selectHql = "SELECT t FROM Store_transaction t" + SqlUtil.getOrderByStatement("t", order);
        final Session session = sessionFactory.getCurrentSession();

        return session.createQuery(selectHql, Transaction.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Transaction> findByProductId(int productId, int offset, int limit, OrderByStatementUtil order) {
        final String selectHql = "SELECT t FROM Store_transaction t WHERE t.product.id = :productId" +
                SqlUtil.getOrderByStatement("t", order);
        final Session session = sessionFactory.getCurrentSession();

        return session.createQuery(selectHql, Transaction.class)
                .setParameter("productId", productId)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    @Override
    public List<Transaction> findByProductIdAndPeriod(int productId, Date start, Date end, int offset, int limit, OrderByStatementUtil order) {
        final String selectHql = "SELECT t FROM Store_transaction t WHERE t.product.id = :productId AND " +
                "t.date BETWEEN :start AND :end" + SqlUtil.getOrderByStatement("t", order);
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
        final String selectHql = "SELECT t FROM Store_transaction t WHERE t.date BETWEEN :start AND :end"
                + SqlUtil.getOrderByStatement("t", order);
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
        final String groupByHql = "SELECT new ru.iac.testtask.dto.TransactionGroupDto(COUNT(t.product.id) as quantity, " +
                "t.product.id, SUM(t.amount) as amount, MAX(t.date) as date) FROM Store_transaction t GROUP BY t.product.id" +
                SqlUtil.getOrderByStatement("", order);
        final Session session = sessionFactory.getCurrentSession();
        return session.createQuery(groupByHql, TransactionGroupDto.class)
                .getResultList();
    }

    @Override
    public List<TransactionGroupDto> getGroupsByAmount(int offset, int limit, OrderByStatementUtil order) {
        final String groupByHql = "SELECT new ru.iac.testtask.dto.TransactionGroupDto(COUNT(t.product.id) as quantity, " +
                "t.amount, SUM(t.amount) as amount, MAX(t.date) as date) FROM Store_transaction t GROUP BY t.amount" +
                SqlUtil.getOrderByStatement("", order);
        final Session session = sessionFactory.getCurrentSession();
        return session.createQuery(groupByHql, TransactionGroupDto.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }

    @Override
    public List<TransactionGroupDto> getGroupsByQuantity(int offset, int limit, OrderByStatementUtil order) {
        final String groupByHql = "SELECT new ru.iac.testtask.dto.TransactionGroupDto(COUNT(t.product.id) as quantity, " +
                "t.quantity, SUM(t.amount) as amount, MAX(t.date) as date) FROM Store_transaction t GROUP BY t.quantity" +
                SqlUtil.getOrderByStatement("", order);
        final Session session = sessionFactory.getCurrentSession();
        return session.createQuery(groupByHql, TransactionGroupDto.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }

    @Override
    public List<TransactionGroupDto> getGroupsByDate(int offset, int limit, OrderByStatementUtil order) {
        final String groupByHql = "SELECT new ru.iac.testtask.dto.TransactionGroupDto(COUNT(t.product.id) as quantity, " +
                "t.date, SUM(t.amount) as amount, MAX(t.date) as date) FROM Store_transaction t GROUP BY t.date" +
                SqlUtil.getOrderByStatement("", order);
        final Session session = sessionFactory.getCurrentSession();
        return session.createQuery(groupByHql, TransactionGroupDto.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }

    @Override
    public BigDecimal getAmountSumByProductId(int productId) {
        final String selectSumHql = "SELECT sum(t.amount) FROM Store_transaction t WHERE t.product.id = :productId";
        final Session session = sessionFactory.getCurrentSession();

        return  (BigDecimal) session.createQuery(selectSumHql)
                .setParameter("productId", productId)
                .uniqueResult();
    }

    @Override
    public BigDecimal getAmountSumByProductIdAndPeriod(int productId, Date start, Date end) {
        final String selectSumHql = "SELECT sum(t.amount) FROM Store_transaction t WHERE t.product.id = :productId AND " +
                "t.date BETWEEN :start AND :end";
        final Session session = sessionFactory.getCurrentSession();

        return (BigDecimal) session.createQuery(selectSumHql)
                .setParameter("productId", productId)
                .setParameter("start", start)
                .setParameter("end", end)
                .uniqueResult();
    }

    @Override
    public BigDecimal getAmountSumByPeriod(Date start, Date end) {
        final String selectSumHql = "SELECT sum(t.amount) FROM Store_transaction t WHERE t.date BETWEEN :start AND :end";
        final Session session = sessionFactory.getCurrentSession();

        return (BigDecimal) session.createQuery(selectSumHql)
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
        final String deleteHql = "DELETE FROM Store_transaction t WHERE t.id = :id";
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
