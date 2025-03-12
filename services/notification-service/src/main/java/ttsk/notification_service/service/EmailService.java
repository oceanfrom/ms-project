package ttsk.notification_service.service;

import jakarta.mail.internet.MimeMessage;
import ttsk.notification_service.model.OrderConfirmation;
import ttsk.notification_service.model.ProductStockNotification;

public interface EmailService {
    void sendEmail(MimeMessage mimeMessage);
    void sendOrderConfirmationEmail(OrderConfirmation orderConfirmation);
    void sendProductStockNotificationEmail(ProductStockNotification productStockNotification);
}
