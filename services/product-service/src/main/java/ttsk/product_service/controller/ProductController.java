package ttsk.product_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import ttsk.product_service.dto.ProductPurchaseRequest;
import ttsk.product_service.dto.ProductRequest;
import ttsk.product_service.service.ProductService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{product-id}")
    public ResponseEntity<?> getProduct(@PathVariable("product-id") UUID productId) {
        return ResponseEntity.ok(productService.getById(productId));
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/categories/{category-id}")
    public ResponseEntity<?> getByCategoryId(@PathVariable("category-id") Long categoryId) {
        return ResponseEntity.ok(productService.getByCategoryId(categoryId));
    }

    @PostMapping
    ResponseEntity<?> createProduct(@Validated @RequestBody ProductRequest productRequest) {
        return ResponseEntity.ok(productService.createProduct(productRequest));
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseProducts(@RequestBody List<ProductPurchaseRequest> productPurchaseRequests) {
        return ResponseEntity.ok(productService.purchaseProducts(productPurchaseRequests));
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@Validated @RequestBody ProductRequest productRequest) {
        productService.updateProduct(productRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{product-id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("product-id") UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }

}
