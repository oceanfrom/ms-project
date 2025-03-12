package ttsk.order_service.dto;

import ttsk.order_service.model.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        CustomerResponse customerResponse,
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        List<ProductPurchaseResponse> products
) {}
