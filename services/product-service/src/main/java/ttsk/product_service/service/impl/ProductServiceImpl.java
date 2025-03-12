package ttsk.product_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttsk.product_service.dto.ProductResponse;
import ttsk.product_service.dto.ProductRequest;
import ttsk.product_service.dto.ProductPurchaseRequest;
import ttsk.product_service.dto.ProductPurchaseResponse;
import ttsk.product_service.dto.ProductStockNotification;
import ttsk.product_service.kafka.ProductProducer;
import ttsk.product_service.mapper.ProductMapper;
import ttsk.product_service.model.Category;
import ttsk.product_service.model.Product;
import ttsk.product_service.repository.ProductRepository;
import ttsk.product_service.service.ProductService;
import ttsk.product_service.validation.ProductValidator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final ProductProducer productProducer;
    private final ProductValidator productValidator;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper,
                              ProductProducer productProducer, ProductValidator productValidator) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.productProducer = productProducer;
        this.productValidator = productValidator;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getById(UUID id) {
        Product product = productValidator.validateProductById(id);
        return productMapper.toProductResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getByCategoryId(Long categoryId) {
        return productRepository.findAllByCategoryId(categoryId)
                .stream()
                .map(productMapper::toProductResponse)
                .toList();
    }

    @Override
    @Transactional
    public UUID createProduct(ProductRequest productRequest) {
        Product product = productRepository.save(productMapper.toProduct(productRequest));
        return product.getId();
    }

    @Override
    @Transactional
    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> productPurchaseRequests) {
        List<UUID> productIds = extractProductIds(productPurchaseRequests);
        List<Product> productsInStock = findProductsInStock(productIds);
        productValidator.validateProductExistence(productIds, productsInStock);

        List<ProductPurchaseResponse> purchasedProducts = processPurchases(productsInStock, productPurchaseRequests);
        productRepository.saveAll(productsInStock);

        return purchasedProducts;
    }

    private List<UUID> extractProductIds(List<ProductPurchaseRequest> requests) {
        return requests.stream()
                .map(ProductPurchaseRequest::productId)
                .toList();
    }

    private List<Product> findProductsInStock(List<UUID> productIds) {
        return productRepository.findAllByIdInOrderById(productIds);
    }

    private List<ProductPurchaseResponse> processPurchases(List<Product> products, List<ProductPurchaseRequest> requests) {
        List<ProductPurchaseResponse> responses = new ArrayList<>();

        var sortedRequests = requests.stream()
                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
                .toList();

        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            ProductPurchaseRequest request = sortedRequests.get(i);

            productValidator.validateStockAvailability(product, request.quantity());
            updateProductStock(product, request.quantity());

            responses.add(productMapper.toProductPurchaseResponse(product, request.quantity()));
        }

        return responses;
    }

    private void updateProductStock(Product product, double purchasedQuantity) {
        product.setAvailableQuantity(product.getAvailableQuantity() - purchasedQuantity);

        if (product.getAvailableQuantity() < 5) {
            ProductStockNotification notification = new ProductStockNotification(
                    product.getId(),
                    product.getName(),
                    product.getAvailableQuantity(),
                    product.getCategory().getName()
            );
            productProducer.sendProductStockNotification(notification);
        }
    }

    @Override
    @Transactional
    public void updateProduct(ProductRequest productRequest) {
        Product product = productValidator.validateProductByRequest(productMapper.toProduct(productRequest));
        updateProductDetails(product, productRequest);
        productRepository.save(product);
    }

    private void updateProductDetails(Product product, ProductRequest productRequest) {
        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setAvailableQuantity(productRequest.availableQuantity());
        product.setPrice(productRequest.price());
        product.setCategory(Category.builder().id(productRequest.categoryId()).build());
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }
}
