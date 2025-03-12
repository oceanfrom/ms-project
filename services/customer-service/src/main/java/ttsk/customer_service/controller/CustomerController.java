package ttsk.customer_service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import ttsk.customer_service.dto.CustomerRequest;
import ttsk.customer_service.dto.CustomerResponse;
import ttsk.customer_service.service.CustomerService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{customer-id}")
    public ResponseEntity<CustomerResponse> getById(@PathVariable("customer-id") UUID customerId) {
        return ResponseEntity.ok(customerService.getById(customerId));
    }

    @GetMapping
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PutMapping
    public ResponseEntity<?> updateCustomer(@Validated @RequestBody CustomerRequest customerRequest) {
        customerService.updateCustomer(customerRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCustomer(UUID uuid) {
        customerService.deleteCustomer(uuid);
        return ResponseEntity.ok().build();
    }
}
