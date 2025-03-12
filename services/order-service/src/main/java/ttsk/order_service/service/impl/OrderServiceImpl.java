package ttsk.order_service.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ttsk.order_service.client.CustomerClient;
import ttsk.order_service.client.ProductClient;
import ttsk.order_service.dto.OrderRequest;
import ttsk.order_service.dto.OrderResponse;
import ttsk.order_service.dto.PurchaseRequest;
import ttsk.order_service.dto.OrderLineRequest;
import ttsk.order_service.dto.OrderConfirmation;
import ttsk.order_service.exception.CustomerNotFoundException;
import ttsk.order_service.exception.OrderProcessException;
import ttsk.order_service.kafka.OrderProducer;
import ttsk.order_service.mapper.OrderMapper;
import ttsk.order_service.repository.OrderRepository;
import ttsk.order_service.service.OrderLineService;
import ttsk.order_service.service.OrderService;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper, CustomerClient customerClient, ProductClient productClient, OrderLineService orderLineService, OrderProducer orderProducer) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.customerClient = customerClient;
        this.productClient = productClient;
        this.orderLineService = orderLineService;
        this.orderProducer = orderProducer;
    }


    @Override
    public OrderResponse getById(UUID id) {
        return orderRepository.findById(id)
                .map(orderMapper::toOrderResponse)
                .orElseThrow(() -> new OrderProcessException("Order with id " + id + " not found"));
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toOrderResponse)
                .toList();
    }

    @Override
    @Transactional
    public UUID createOrder(OrderRequest orderRequest) {
        var customer = customerClient.findCustomerById(orderRequest.customerId())
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));
        var purchasedProducts = productClient.purchaseProducts(orderRequest.products());

        var order = orderRepository.save(orderMapper.toOrder(orderRequest));

        for (PurchaseRequest purchaseRequest : orderRequest.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        log.info("Created order: {}", orderRequest.reference() + " " + orderRequest.amount() + " " + orderRequest.paymentMethod() + " " + purchasedProducts);

        orderProducer.sendOrderConfirmation(new OrderConfirmation(
                customer,
                orderRequest.reference(),
                orderRequest.amount(),
                orderRequest.paymentMethod(),
                purchasedProducts
        ));


        return order.getId();
    }

}
