package az.shopery.utils.validation;

import az.shopery.utils.annotation.ValidProductCondition;
import az.shopery.utils.enums.ProductCondition;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductConditionValidator  implements ConstraintValidator<ValidProductCondition, ProductCondition> {

    private Set<ProductCondition> allowedValues;

    @Override
    public void initialize(ValidProductCondition constraintAnnotation) {
        if (constraintAnnotation.anyOf().length > 0) {
            this.allowedValues = Arrays.stream(constraintAnnotation.anyOf()).collect(Collectors.toSet());
        }
    }

    @Override
    public boolean isValid(ProductCondition value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        if (allowedValues != null && !allowedValues.isEmpty()) {
            return allowedValues.contains(value);
        }
        return true;
    }
}
