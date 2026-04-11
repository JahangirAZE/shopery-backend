package az.shopery.model.dto.redis;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CachedPasswordResetData {
    String token;
    String userEmail;
    LocalDateTime expiryDate;
    LocalDateTime linkLastSentAt;
}
