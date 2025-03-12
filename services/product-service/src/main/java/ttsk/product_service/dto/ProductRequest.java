package ttsk.product_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record ProductRequest(
        @NotNull(message = "Name cannot be null")
        String name,
        @NotNull(message = "Description cannot be null")
        String description,
        @Positive(message = "Quantity is mandatory")
        double availableQuantity,
        @NotNull(message = "Price cannot be null")
        BigDecimal price,
        @NotNull(message = "Category ID cannot be null")
        Long categoryId
) { }
