package ttsk.product_service.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ttsk.product_service.dto.ProductStockNotification;

import java.util.UUID;

@Service
public class ProductProducer {
    private final KafkaTemplate<String, ProductStockNotification> kafkaTemplate;

    @Autowired
    public ProductProducer(KafkaTemplate<String, ProductStockNotification> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendProductStockNotification(ProductStockNotification productStockNotification) {
        kafkaTemplate.send("productStockNotification", productStockNotification);
    }
}
