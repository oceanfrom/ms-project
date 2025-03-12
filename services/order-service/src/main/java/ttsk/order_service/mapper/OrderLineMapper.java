package ttsk.order_service.mapper;

import org.mapstruct.Mapper;
import ttsk.order_service.dto.OrderLineRequest;
import ttsk.order_service.model.OrderLine;

@Mapper(componentModel = "spring")
public interface OrderLineMapper {
    OrderLine toOrderLine(OrderLineRequest request);
}
