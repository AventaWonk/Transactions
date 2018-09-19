package ru.iac.testtask.dao;

import ru.iac.testtask.dto.TransactionGroupDto;
import ru.iac.testtask.model.Transaction;
import ru.iac.testtask.util.OrderByStatementUtil;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TransactionDao {

    int getTransactionCount();

    int getTransactionCountByProductId(int productId);

    int getTransactionCountByProductIdAndPeriod(int productId, Date start, Date endDate);

    int getTransactionCountByPeriod(Date startDate, Date endDate);

    int getTransactionCountWithGroupByProduct();

    int getTransactionCountWithGroupByAmount();

    int getTransactionCountWithGroupByQuantity();

    int getTransactionCountWithGroupByDate();


    List<Transaction> findAll(int offset, int limit, OrderByStatementUtil order);

    // @TODO refactor Create Parameter Object
    List<Transaction> findByProductId(int productId, int offset, int limit, OrderByStatementUtil order);

    List<Transaction> findByProductIdAndPeriod(int productId, Date start, Date end, int offset, int limit, OrderByStatementUtil order);

    List<Transaction> findByPeriod(Date start, Date end, int offset, int limit, OrderByStatementUtil order);

    // @TODO refactor Write group statement generator
    List<TransactionGroupDto> getGroupsByProduct(int offset, int limit, OrderByStatementUtil order);

    List<TransactionGroupDto> getGroupsByAmount(int offset, int limit, OrderByStatementUtil order);

    List<TransactionGroupDto> getGroupsByQuantity(int offset, int limit, OrderByStatementUtil order);

    List<TransactionGroupDto> getGroupsByDate(int offset, int limit, OrderByStatementUtil order);


    BigDecimal getSumByProductId(int productId);

    BigDecimal getSumByProductIdAndPeriod(int productId, Date start, Date end);

    BigDecimal getSumByPeriod(Date start, Date end);


    Transaction findById(int id);

    void create(Transaction transaction);

    void update(Transaction transaction);

    void remove(int id);
}
