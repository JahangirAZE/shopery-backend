package az.shopery.repository;

import az.shopery.model.entity.CartEntity;
import az.shopery.model.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<CartEntity, UUID> {
    @Query("SELECT c FROM CartEntity c LEFT JOIN FETCH c.items WHERE c.user = :user")
    Optional<CartEntity> findByUserWithItems(@Param("user") UserEntity userEntity);
}
