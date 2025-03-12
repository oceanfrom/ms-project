package ttsk.auth_service.mapper;

import org.mapstruct.Mapper;
import ttsk.auth_service.dto.RegisterRequest;
import ttsk.auth_service.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer toCustomer(RegisterRequest registerRequest);
}
