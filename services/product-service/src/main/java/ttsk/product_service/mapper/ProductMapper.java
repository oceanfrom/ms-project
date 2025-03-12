package ttsk.product_service.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import ttsk.product_service.dto.ProductPurchaseResponse;
import ttsk.product_service.dto.ProductRequest;
import ttsk.product_service.dto.ProductResponse;
import ttsk.product_service.model.Category;
import ttsk.product_service.model.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "category", source = "categoryId", qualifiedByName = "mapCategory")
    Product toProduct(ProductRequest productRequest);
    @Mapping(target = "categoryName", source = "product.category.name")
    ProductResponse toProductResponse(Product product);
    @Mapping(target = "productId", source = "product.id")
//    @Mapping(target = "name", source = "product.name")
//    @Mapping(target = "description", source = "product.description")
//    @Mapping(target = "price", source = "product.price")
//    @Mapping(target = "quantity", source = "quantity")
    @Mapping(target = "categoryName", source = "product.category.name")
    ProductPurchaseResponse toProductPurchaseResponse(Product product, double quantity);

    @Named("mapCategory")
    default Category mapCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }
        return Category.builder()
                .id(categoryId)
                .build();
    }
}

