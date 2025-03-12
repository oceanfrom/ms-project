package ttsk.order_service.dto;

import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String name,
        String username
) {}
