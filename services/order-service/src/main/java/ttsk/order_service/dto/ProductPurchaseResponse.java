package ttsk.order_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductPurchaseResponse(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        String categoryName
) {}
