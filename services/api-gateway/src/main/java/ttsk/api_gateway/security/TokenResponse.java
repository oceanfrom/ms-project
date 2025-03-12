package ttsk.api_gateway.security;

import java.util.UUID;

public record TokenResponse(
        UUID id,
        String username,
        String accessToken,
        String refreshToken
) {}
