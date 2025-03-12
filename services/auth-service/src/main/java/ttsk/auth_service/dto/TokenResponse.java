package ttsk.auth_service.dto;

import java.util.UUID;

public record TokenResponse(
        UUID id,
        String username,
        String accessToken,
        String refreshToken) {
}
