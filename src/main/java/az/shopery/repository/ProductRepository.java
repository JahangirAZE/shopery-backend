package az.shopery.repository;

import az.shopery.model.entity.ProductEntity;
import az.shopery.utils.enums.ProductCategory;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, UUID> {
    Page<ProductEntity> findByShopId(UUID shopId, Pageable pageable);
    Page<ProductEntity> findByCategory(ProductCategory category, Pageable pageable);
}
