package az.shopery.utils.validation;

import az.shopery.utils.annotation.ValidAddressType;
import az.shopery.utils.enums.AddressType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class AddressTypeValidator implements ConstraintValidator<ValidAddressType, AddressType> {

    private Set<AddressType> allowedTypes;

    @Override
    public void initialize(ValidAddressType constraintAnnotation) {
        if (constraintAnnotation.anyOf().length > 0) {
            this.allowedTypes = Arrays.stream(constraintAnnotation.anyOf()).collect(Collectors.toSet());
        }
    }

    @Override
    public boolean isValid(AddressType addressType, ConstraintValidatorContext constraintValidatorContext) {
        if (addressType == null) {
            return false;
        }
        if (allowedTypes != null && !allowedTypes.isEmpty()) {
            return allowedTypes.contains(addressType);
        }
        return true;
    }
}
