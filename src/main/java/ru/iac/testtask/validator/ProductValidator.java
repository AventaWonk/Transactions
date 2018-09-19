package ru.iac.testtask.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.iac.testtask.model.Product;

@Component
public class ProductValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "name", "name.required", "Name can't be empty");
        Product product = (Product) o;
        if (product.getName().length() > 255) {
            errors.rejectValue("name", "name.toLong", "Name can't be longer than 255 characters");
        }
    }

}
