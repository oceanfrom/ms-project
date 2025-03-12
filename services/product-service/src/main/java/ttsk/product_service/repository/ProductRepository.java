package ttsk.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ttsk.product_service.model.Product;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    List<Product> findAllByIdInOrderById(List<UUID> productsId);
    List<Product> findAllByCategoryId(Long id);
}
