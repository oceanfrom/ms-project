package ttsk.product_service.dto;
import java.util.UUID;

public record ProductStockNotification(
        UUID productId,
        String name,
        double availableQuantity,
        String categoryName
) {}
