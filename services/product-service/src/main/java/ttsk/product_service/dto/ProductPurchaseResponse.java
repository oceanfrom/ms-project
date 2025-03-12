package ttsk.product_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductPurchaseResponse(
        UUID productId,
        String name,
        String description,
        BigDecimal price,
        double quantity,
        String categoryName
) {}