package ttsk.auth_service.service;

import ttsk.auth_service.dto.RegisterRequest;
import ttsk.auth_service.model.Customer;

import java.util.UUID;

public interface CustomerService {
    Customer getByUserName(String username);
    Customer getById(UUID id);
    UUID createCustomer(RegisterRequest registerRequest);
}
