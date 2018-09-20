package ru.iac.testtask.dto;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionGroupDto <T> {

    private long count;

    private T grouped;

    private BigDecimal total;

    private Date last;

    public TransactionGroupDto(long count, T grouped, BigDecimal total, Date last) {
        this.count = count;
        this.grouped = grouped;
        this.total = total;
        this.last = last;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public T getGrouped() {
        return grouped;
    }

    public void setGrouped(T grouped) {
        this.grouped = grouped;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Date getLast() {
        return last;
    }

    public void setLast(Date last) {
        this.last = last;
    }
}