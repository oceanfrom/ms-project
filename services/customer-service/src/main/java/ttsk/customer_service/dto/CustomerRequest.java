package ttsk.customer_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import ttsk.customer_service.model.Address;

public record CustomerRequest(
        @NotNull(message = "Firstname is required")
        String name,
        @NotNull(message = "Password is required")
        @Email(message = "Is not availed email address")
        @NotNull(message = "Email is required")
        String username,
        @NotNull(message = "Password is required")
        String password,
        Address address
) {
}
