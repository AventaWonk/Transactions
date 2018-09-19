package ru.iac.testtask.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.iac.testtask.model.Shop;

@Component
public class ShopValidator implements Validator {

    @Override
    public boolean supports(Class clazz) {
        return Shop.class.equals(clazz);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "address", "address.required", "Address can't be empty");
        Shop shop = (Shop) o;
        if (shop.getAddress().length() > 255) {
            errors.rejectValue("address", "address.toLong", "Address can't be longer than 255 characters");
        }
    }

}
