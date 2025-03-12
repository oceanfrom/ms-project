package ttsk.order_service.service;

import ttsk.order_service.dto.OrderRequest;
import ttsk.order_service.dto.OrderResponse;
import java.util.List;
import java.util.UUID;

public interface OrderService {
    OrderResponse getById(UUID id);
    List<OrderResponse> getAllOrders();
    UUID createOrder(OrderRequest orderRequest);
}
