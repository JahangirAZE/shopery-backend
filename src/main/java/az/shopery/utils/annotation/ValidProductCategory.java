package az.shopery.utils.annotation;

import az.shopery.utils.enums.ProductCategory;
import az.shopery.utils.validation.ProductCategoryValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = ProductCategoryValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidProductCategory {
    String message() default "Invalid product category provided.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    ProductCategory[] anyOf() default {};
}
