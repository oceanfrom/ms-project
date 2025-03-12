package ttsk.product_service.service;

import ttsk.product_service.dto.ProductPurchaseRequest;
import ttsk.product_service.dto.ProductPurchaseResponse;
import ttsk.product_service.dto.ProductRequest;
import ttsk.product_service.dto.ProductResponse;
import ttsk.product_service.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponse getById(UUID id);
    List<ProductResponse> getAllProducts();
    List<ProductResponse> getByCategoryId(Long categoryId);
    UUID createProduct(ProductRequest productRequest);
    List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> productPurchaseRequest);
    void updateProduct(ProductRequest productRequest);
    void deleteProduct(UUID id);
}
