package ttsk.product_service.validation;
import org.springframework.stereotype.Component;
import ttsk.product_service.exception.ProductNotFoundException;
import ttsk.product_service.exception.ProductPurchaseException;
import ttsk.product_service.model.Product;
import ttsk.product_service.repository.ProductRepository;

import java.util.List;
import java.util.UUID;

@Component
public class ProductValidator {

    private final ProductRepository productRepository;

    public ProductValidator(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void validateProductExistence(List<UUID> productIds, List<Product> productsInStock) {
        if (productIds.size() != productsInStock.size()) {
            throw new ProductPurchaseException("One or more products do not exist.");
        }
    }

    public void validateStockAvailability(Product product, double requestedQuantity) {
        if (product.getAvailableQuantity() < requestedQuantity) {
            throw new ProductPurchaseException("Incorrect product quantity in stock: " + product.getId());
        }
    }

    public Product validateProductById(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
    }

    public Product validateProductByRequest(Product productRequest) {
        return productRepository.findById(productRequest.getId())
                .orElseThrow(() -> new ProductNotFoundException("Product " + productRequest.getName() + " not found"));
    }
}
