package ttsk.notification_service.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ttsk.notification_service.exception.NotificationException;
import ttsk.notification_service.model.OrderConfirmation;
import ttsk.notification_service.model.ProductStockNotification;
import ttsk.notification_service.service.EmailService;

@Service
@Slf4j
public class NotificationConsumer {

    private final EmailService emailService;

    public NotificationConsumer(EmailService emailService) {
        this.emailService = emailService;
    }

    @KafkaListener(topics = "orderTopic")
    public void consumeOrderConfirmationNotifications(OrderConfirmation orderConfirmation){
        try {
            emailService.sendOrderConfirmationEmail(orderConfirmation);
            log.info("Order confirmation email sent successfully.");
        } catch (NotificationException e) {
            log.error("Error sending order confirmation email: {}", e.getMessage(), e);
        }
    }

    @KafkaListener(topics = "productStockNotification")
    public void consumeproductStockNotifications(ProductStockNotification productStockNotification) {
        try {
            emailService.sendProductStockNotificationEmail(productStockNotification);
            log.info("Product stock notification email sent successfully.");
        } catch (NotificationException e) {
            log.error("Error sending product stock notification email: {}", e.getMessage(), e);
        }
    }
}
