package ttsk.customer_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttsk.customer_service.dto.CustomerRequest;
import ttsk.customer_service.dto.CustomerResponse;
import ttsk.customer_service.exception.CustomerNotFoundException;
import ttsk.customer_service.mapper.CustomerMapper;
import ttsk.customer_service.model.Customer;
import ttsk.customer_service.repository.CustomerRepository;
import ttsk.customer_service.service.CustomerService;

import java.util.List;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
   private final CustomerMapper customerMapper;



    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper, CustomerMapper customerMapper1) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper1;
    }

    @Override
    public CustomerResponse getById(UUID id) {
        return customerRepository.findById(id)
                .map(customerMapper::toCustomerResponse)
                .orElseThrow(() -> new CustomerNotFoundException("Customer with id " + id + " not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toCustomerResponse)
                .toList();
    }


    @Override
    @Transactional
    public void updateCustomer(CustomerRequest customerRequest) {
       var customer = customerRepository.findCustomerByUsername(customerRequest.username())
                .orElseThrow(() -> new CustomerNotFoundException("Customer with email " + customerRequest.username() + " not found"));
       updateCustomer(customer, customerRequest);
       customerRepository.save(customer);
    }

    private void updateCustomer(Customer customer, CustomerRequest customerRequest) {
        customer.setUsername(customerRequest.username());
        customer.setUsername(customerRequest.username());
        customer.setAddress(customerRequest.address());
    }

    @Override
    @Transactional
    public void deleteCustomer(UUID uuid) {
        customerRepository.deleteById(uuid);
    }
}
