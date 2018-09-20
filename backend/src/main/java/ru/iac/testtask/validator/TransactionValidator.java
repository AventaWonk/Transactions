package ru.iac.testtask.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.iac.testtask.model.Transaction;

import java.math.BigDecimal;

@Component
public class TransactionValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return Transaction.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "amount", "amount.empty", "Invalid amount value");
        Transaction transaction = (Transaction) o;
        if (transaction.getQuantity() <= 0) {
            errors.rejectValue("quantity", "quantity.less", "Quantity can't be less than 1");
        }

        final BigDecimal amount = transaction.getAmount();
        if (amount != null && amount.signum() == -1) {
            errors.rejectValue("amount", "amount.negative", "Amount can't be negative");
        }

        if (amount != null && amount.precision() > 12) {
            errors.rejectValue("amount", "a.toLong", "Amount can't be bigger than 10^11");
        }
    }
}
