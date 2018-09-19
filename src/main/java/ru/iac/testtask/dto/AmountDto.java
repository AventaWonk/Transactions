package ru.iac.testtask.dto;

import java.math.BigDecimal;
import java.util.List;

public final class AmountDto <T> {

    private List<T> operations;
    private BigDecimal totalAmount;
    private int totalOperations;

    public AmountDto(List<T> operations, BigDecimal totalAmount, int totalOperations) {
        this.operations = operations;
        this.totalAmount = totalAmount;
        this.totalOperations = totalOperations;
    }

    public List<T> getOperations() {
        return operations;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public int getTotalOperations() {
        return totalOperations;
    }
}
