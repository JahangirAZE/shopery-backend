package az.shopery.repository;

import az.shopery.model.entity.BlogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BlogRepository extends JpaRepository<BlogEntity, Integer> {
    List<BlogEntity> getBlogsByUserEmail(String email);
}
