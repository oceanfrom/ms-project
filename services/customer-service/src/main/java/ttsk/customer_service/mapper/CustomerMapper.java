package ttsk.customer_service.mapper;
import org.mapstruct.Mapper;
import ttsk.customer_service.dto.CustomerRequest;
import ttsk.customer_service.dto.CustomerResponse;
import ttsk.customer_service.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(CustomerRequest customerRequest);
    CustomerResponse toCustomerResponse(Customer customer);
}
