package ttsk.product_service.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String description,
        double availableQuantity,
        BigDecimal price,
        String categoryName
) {}