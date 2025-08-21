package az.shopery.repository;

import az.shopery.model.entity.UserEntity;
import az.shopery.model.entity.WishlistEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WishlistRepository extends JpaRepository<WishlistEntity, UUID> {
    @Query("SELECT w FROM WishlistEntity w LEFT JOIN FETCH w.products WHERE w.user = :user")
    Optional<WishlistEntity> findByUserWithProducts(@Param("user") UserEntity userEntity);
}
