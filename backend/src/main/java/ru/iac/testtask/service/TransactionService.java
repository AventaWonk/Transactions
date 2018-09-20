package ru.iac.testtask.service;

import ru.iac.testtask.dto.AmountDto;
import ru.iac.testtask.dto.TransactionGroupDto;
import ru.iac.testtask.model.Transaction;
import ru.iac.testtask.util.OrderByStatementUtil;

import java.util.Date;
import java.util.List;

public interface TransactionService {

    int getTransactionCount();

    List<Transaction> getAllTransactions(int offset, int limit, OrderByStatementUtil order);

    AmountDto<Transaction> getTotalAmountByProductId(int productId, int offset, int limit, OrderByStatementUtil order);

    AmountDto<Transaction> getTotalAmountByProductIdAndPeriod(int productId,
                                                              Date start,
                                                              Date end,
                                                              int offset,
                                                              int limit,
                                                              OrderByStatementUtil order);

    AmountDto<Transaction> getTotalAmountByPeriod(Date start, Date end, int offset, int limit, OrderByStatementUtil order);

    AmountDto<TransactionGroupDto> getTransactionGroupsByColumn(String column, int offset, int limit, OrderByStatementUtil order);

    Transaction getTransactionById(int id);

    void addTransaction(Transaction transaction);

    void updateTransaction(Transaction transaction);

    void deleteTransaction(int id);
}
