package ttsk.order_service.mapper;

import org.mapstruct.Mapper;
import ttsk.order_service.dto.OrderRequest;
import ttsk.order_service.dto.OrderResponse;
import ttsk.order_service.model.Order;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderResponse toOrderResponse(Order order);
    Order toOrder(OrderRequest orderRequest);
}
