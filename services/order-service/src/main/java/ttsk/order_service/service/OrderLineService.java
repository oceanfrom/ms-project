package ttsk.order_service.service;

import ttsk.order_service.dto.OrderLineRequest;

public interface OrderLineService {
    void saveOrderLine(OrderLineRequest orderLineRequest);
}
