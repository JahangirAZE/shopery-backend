package az.shopery.utils.annotation;

import az.shopery.utils.enums.ProductCondition;
import az.shopery.utils.validation.ProductConditionValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ProductConditionValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidProductCondition {
    String message() default "Invalid product condition provided.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    ProductCondition[] anyOf() default {};
}
