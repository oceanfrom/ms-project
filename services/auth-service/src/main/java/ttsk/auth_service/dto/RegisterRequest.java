package ttsk.auth_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ttsk.auth_service.model.Address;

public record RegisterRequest(
        @NotBlank(message = "Username name is required") String name,
        @Email(message = "Email should be valid") @NotBlank(message = "Email is required") String username,
        @NotBlank(message = "Password is required") String password,
        @NotNull(message = "Address is required") Address address
) {}
