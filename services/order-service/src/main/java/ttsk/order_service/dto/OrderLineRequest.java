package ttsk.order_service.dto;

import java.util.UUID;

public record OrderLineRequest(
        UUID id,
        UUID orderId,
        UUID productId,
        double quantity
) {}
