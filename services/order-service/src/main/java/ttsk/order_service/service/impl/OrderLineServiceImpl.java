package ttsk.order_service.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ttsk.order_service.dto.OrderLineRequest;
import ttsk.order_service.mapper.OrderLineMapper;
import ttsk.order_service.repository.OrderLineRepository;
import ttsk.order_service.service.OrderLineService;

@Service
public class OrderLineServiceImpl implements OrderLineService {
    private final OrderLineRepository orderLineRepository;
    private final OrderLineMapper orderLineMapper;

    @Autowired
    public OrderLineServiceImpl(OrderLineRepository orderLineRepository, OrderLineMapper orderLineMapper) {
        this.orderLineRepository = orderLineRepository;
        this.orderLineMapper = orderLineMapper;
    }

    @Override
    public void saveOrderLine(OrderLineRequest orderLineRequest) {
        orderLineRepository.save(orderLineMapper.toOrderLine(orderLineRequest));
    }
}
