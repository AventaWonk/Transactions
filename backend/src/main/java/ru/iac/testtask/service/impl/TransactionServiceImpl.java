package ru.iac.testtask.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.iac.testtask.dao.TransactionDao;
import ru.iac.testtask.dto.AmountDto;
import ru.iac.testtask.dto.TransactionGroupDto;
import ru.iac.testtask.model.Transaction;
import ru.iac.testtask.service.TransactionService;
import ru.iac.testtask.util.OrderByStatementUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {

    private TransactionDao transactionDao;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public int getTransactionCount() {
        return this.transactionDao.getTransactionCount();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Transaction> getAllTransactions(int offset, int limit, OrderByStatementUtil order) {
        return this.transactionDao.findAll(offset, limit, order);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public AmountDto<Transaction> getTotalAmountByProductId(int productId, int offset, int limit, OrderByStatementUtil order) {
        int total = this.transactionDao.getTransactionCountByProductId(productId);
        List<Transaction> transactionList = this.transactionDao.findByProductId(productId, offset, limit, order);
        BigDecimal totalAmount = this.transactionDao.getAmountSumByProductId(productId);

        return new AmountDto<>(transactionList, totalAmount, total);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public AmountDto<Transaction> getTotalAmountByProductIdAndPeriod(int productId,
                                                                     Date start,
                                                                     Date end,
                                                                     int offset,
                                                                     int limit,
                                                                     OrderByStatementUtil order) {
        int total = this.transactionDao.getTransactionCountByProductIdAndPeriod(productId, start, end);
        List<Transaction> transactionList = this.transactionDao.
                findByProductIdAndPeriod(productId, start, end, offset, limit, order);
        BigDecimal totalAmount = this.transactionDao.getAmountSumByProductIdAndPeriod(productId, start, end);

        return new AmountDto<>(transactionList, totalAmount, total);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public AmountDto<Transaction> getTotalAmountByPeriod(Date start, Date end, int offset, int limit, OrderByStatementUtil order) {
        int total = this.transactionDao.getTransactionCountByPeriod(start, end);
        List<Transaction> transactionList = this.transactionDao.findByPeriod(start, end, offset, limit, order);
        BigDecimal totalAmount = this.transactionDao.getAmountSumByPeriod(start, end);

        return new AmountDto<>(transactionList, totalAmount, total);
    }

    @Override
    public AmountDto<TransactionGroupDto> getTransactionGroupsByColumn(String column, int offset, int limit, OrderByStatementUtil order) {
        List<TransactionGroupDto> groupedTransactions;
        int countOfGroups;
        switch (column) {
            case "product":
                countOfGroups = this.transactionDao.getCountOfGroupsByProduct();
                groupedTransactions = this.transactionDao.getGroupsByProduct(offset, limit, order);
                break;
            case "quantity":
                countOfGroups = this.transactionDao.getCountOfGroupByQuantity();
                groupedTransactions = this.transactionDao.getGroupsByQuantity(offset, limit, order);
                break;
            case "amount":
                countOfGroups = this.transactionDao.getCountOfGroupsByAmount();
                groupedTransactions = this.transactionDao.getGroupsByAmount(offset, limit, order);
                break;
            case "date":
                countOfGroups = this.transactionDao.getCountOfGroupByDate();
                groupedTransactions = this.transactionDao.getGroupsByDate(offset, limit, order);
                break;

                default:
                    return null;
        }

        return new AmountDto<>(groupedTransactions, null, countOfGroups);
    }


    /**
     * Gets an existing transaction by id
     *
     * @param id id of target transaction
     * @return an existing transaction
     */
    @Override
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public Transaction getTransactionById(int id) {
        return this.transactionDao.findById(id);
    }

    /**
     * Adds a new transactions
     *
     * @param transaction new transactions
     */
    @Override
    public void addTransaction(Transaction transaction) {
        transaction.setDate(new Date());
        this.transactionDao.create(transaction);
    }

    /**
     * Updates an existing transaction
     *
     * @param transaction existing transaction with updated fields (excepts id)
     */
    @Override
    public void updateTransaction(Transaction transaction) {
        this.transactionDao.update(transaction);
    }

    @Override
    public void deleteTransaction(int id) {
        this.transactionDao.remove(id);
    }

    @Autowired
    public void setTransactionDao(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }
}
