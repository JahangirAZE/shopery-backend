package az.shopery.utils.validation;

import az.shopery.utils.annotation.ValidProductCategory;
import az.shopery.utils.enums.ProductCategory;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductCategoryValidator implements ConstraintValidator<ValidProductCategory, ProductCategory> {

    private Set<ProductCategory> allowedValues;

    @Override
    public void initialize(ValidProductCategory constraintAnnotation) {
        if (constraintAnnotation.anyOf().length > 0) {
            this.allowedValues = Arrays.stream(constraintAnnotation.anyOf()).collect(Collectors.toSet());
        }
    }

    @Override
    public boolean isValid(ProductCategory value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (allowedValues != null && !allowedValues.isEmpty()) {
            return allowedValues.contains(value);
        }
        return true;
    }
}
