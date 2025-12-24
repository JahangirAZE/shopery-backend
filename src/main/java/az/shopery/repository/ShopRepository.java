package az.shopery.repository;

import az.shopery.model.entity.ShopEntity;
import az.shopery.model.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopRepository extends JpaRepository<ShopEntity, UUID> {
    Boolean existsByUser(UserEntity userEntity);
    Boolean existsByShopName(String shopName);
    Optional<ShopEntity> findByUserEmail(String userEmail);
    Optional<ShopEntity> findByUser(UserEntity userEntity);
    @Query("SELECT s FROM ShopEntity s LEFT JOIN FETCH s.products WHERE s.id = :id")
    Optional<ShopEntity> findByIdWithProducts(@Param("id") UUID id);
    @Query("SELECT s FROM ShopEntity s LEFT JOIN FETCH s.products WHERE s.shopName = :shopName")
    Optional<ShopEntity> findByShopNameWithProducts(@Param("shopName") String shopName);
}
