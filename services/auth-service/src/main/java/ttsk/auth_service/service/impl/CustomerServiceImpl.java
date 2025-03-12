package ttsk.auth_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttsk.auth_service.dto.RegisterRequest;
import ttsk.auth_service.exception.CustomerNotFoundException;
import ttsk.auth_service.mapper.CustomerMapper;
import ttsk.auth_service.model.Customer;
import ttsk.auth_service.model.Role;
import ttsk.auth_service.repository.CustomerRepository;
import ttsk.auth_service.service.CustomerService;

import java.util.Set;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getByUserName(String username) {
        return customerRepository.findByUsername(username)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public Customer getById(UUID id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
    }

    @Override
    @Transactional
    public UUID createCustomer(RegisterRequest registerRequest) {
        if (customerRepository.findByUsername(registerRequest.username()).isPresent()) {
            throw new IllegalStateException("User already exists.");
        }
        var customer = customerMapper.toCustomer(registerRequest);
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_USER);
        customer.setRoles(roles);
        customerRepository.save(customer);
        return customer.getId();
    }
}
