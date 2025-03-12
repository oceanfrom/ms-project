package ttsk.customer_service.dto;

import ttsk.customer_service.model.Address;

import java.util.UUID;

public record CustomerResponse(
        UUID id,
        String name,
        String username,
        Address address
) {}
