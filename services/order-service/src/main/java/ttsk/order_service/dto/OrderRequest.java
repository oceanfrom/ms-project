package ttsk.order_service.dto;

import jakarta.validation.constraints.*;
import ttsk.order_service.model.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderRequest(
        @NotNull(message = "Customer ID cannot be null")
        UUID customerId,
        @NotBlank(message = "Reference cannot be blank")
        @Size(max = 50, message = "Reference must be at most 50 characters long")
        String reference,
        @NotNull(message = "Order amount is required")
        @Positive(message = "Order amount must be positive")
        BigDecimal amount,
        @NotNull(message = "Payment method is required")
        PaymentMethod paymentMethod,
        @NotEmpty(message = "You should at least purchase one product")
        List<PurchaseRequest> products
) {}
