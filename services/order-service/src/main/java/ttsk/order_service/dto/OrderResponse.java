package ttsk.order_service.dto;

import ttsk.order_service.model.PaymentMethod;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        UUID customerId,
        String reference,
        BigDecimal amount,
        PaymentMethod paymentMethod
) {}