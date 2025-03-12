package ttsk.auth_service.dto;

import jakarta.validation.constraints.NotNull;

public record LoginRequest(
        @NotNull(message = "username mush been not null") String username,
        @NotNull(message = "password must be not null") String password
) {}
