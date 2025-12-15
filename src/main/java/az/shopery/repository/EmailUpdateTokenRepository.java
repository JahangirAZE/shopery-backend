package az.shopery.repository;

import az.shopery.model.entity.EmailUpdateTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface EmailUpdateTokenRepository extends JpaRepository<EmailUpdateTokenEntity, UUID> {
   Optional<EmailUpdateTokenEntity> findByEmail(String token);
    void deleteByExpiryDateBefore(LocalDateTime now);
}
