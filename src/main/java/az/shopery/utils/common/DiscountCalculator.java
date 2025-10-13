package az.shopery.utils.common;

import az.shopery.model.dto.shared.DiscountDto;
import lombok.experimental.UtilityClass;
import java.math.BigDecimal;
import java.math.RoundingMode;

@UtilityClass
public class DiscountCalculator {

    public static DiscountDto calculateDiscountFromOriginalPrice(BigDecimal currentPrice, BigDecimal originalPrice) {
        if (originalPrice == null || currentPrice.compareTo(originalPrice) >= 0) {
            return null;
        }

        BigDecimal difference = originalPrice.subtract(currentPrice);
        BigDecimal percentageDecimal = difference.divide(originalPrice, 2, RoundingMode.HALF_UP);
        int percentage = percentageDecimal.multiply(new BigDecimal("100")).intValue();

        return DiscountDto.builder()
                .percentage(percentage)
                .originalPrice(originalPrice)
                .build();
    }
}
