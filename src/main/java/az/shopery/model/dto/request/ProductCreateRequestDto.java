package az.shopery.model.dto.request;

import az.shopery.utils.annotation.ValidProductCategory;
import az.shopery.utils.annotation.ValidProductCondition;
import az.shopery.utils.enums.ProductCategory;
import az.shopery.utils.enums.ProductCondition;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCreateRequestDto {
    @NotBlank(message = "Product name cannot be empty!")
    @Size(min = 3, max = 255, message = "Product name must be between 3 and 255 characters long.")
    String productName;
    @Size(max = 2000, message = "Maximum product description length exceeded!")
    String description;
    @NotNull
    @ValidProductCondition(message = "You must specify a valid product condition (e.g., NEW, USED).")
    ProductCondition condition;
    @NotNull
    @ValidProductCategory(message = "You must specify a valid product category (e.g., HOME, FASHION).")
    ProductCategory category;
    @NotNull
    @Positive
    BigDecimal price;
    @NotNull
    @PositiveOrZero
    Integer stockQuantity;
}
