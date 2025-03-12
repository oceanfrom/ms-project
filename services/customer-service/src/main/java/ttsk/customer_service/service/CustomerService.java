package ttsk.customer_service.service;

import ttsk.customer_service.dto.CustomerRequest;
import ttsk.customer_service.dto.CustomerResponse;
import ttsk.customer_service.model.Customer;

import java.util.List;
import java.util.UUID;

public interface CustomerService {
    CustomerResponse getById(UUID id);
    List<CustomerResponse> getAllCustomers();
    void updateCustomer(CustomerRequest customerRequest);
    void deleteCustomer(UUID uuid);
}
