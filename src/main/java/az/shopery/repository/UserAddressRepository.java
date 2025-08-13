package az.shopery.repository;

import az.shopery.model.entity.UserAddressEntity;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddressEntity, UUID> {
    List<UserAddressEntity> findAllByUserId(UUID userId);
    boolean existsByUserIdAndIsDefaultTrue(UUID userId);
}
