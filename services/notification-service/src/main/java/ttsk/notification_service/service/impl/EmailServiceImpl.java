package ttsk.notification_service.service.impl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import ttsk.notification_service.model.OrderConfirmation;
import ttsk.notification_service.model.ProductStockNotification;
import ttsk.notification_service.exception.NotificationException;
import ttsk.notification_service.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendEmail(MimeMessage mimeMessage) {
        mailSender.send(mimeMessage);
    }

    @Override
    public void sendOrderConfirmationEmail(OrderConfirmation orderConfirmation) {
        try {
            MimeMessage mimeMessage = createOrderConfirmationEmail(orderConfirmation);
            sendEmail(mimeMessage);
        } catch (MessagingException e) {
            throw new NotificationException("Error during sending order confirmation email.");
        }
    }

    @Override
    public void sendProductStockNotificationEmail(ProductStockNotification productStockNotification) {
        try {
            MimeMessage mimeMessage = createProductStockNotificationEmail(productStockNotification);
            sendEmail(mimeMessage);
        } catch (MessagingException e) {
            throw new NotificationException("Error during sending product stock notification email.");
        }
    }

    private MimeMessage createOrderConfirmationEmail(OrderConfirmation orderConfirmation) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        String subject = "Order Confirmation - " + orderConfirmation.getOrderReference();
        StringBuilder body = new StringBuilder();
        body.append(String.format("Dear %s,", orderConfirmation.getCustomer().getName())).append("\n\n");
        body.append("Thank you for your order. Here are the details:\n\n");
        body.append(String.format("Order Reference: %s\n", orderConfirmation.getOrderReference()));
        body.append(String.format("Total Amount: $%.2f\n", orderConfirmation.getAmount()));
        body.append(String.format("Payment Method: %s\n\n", orderConfirmation.getPaymentMethod()));

        body.append("Products:\n");
        orderConfirmation.getProducts().forEach(product -> {
            body.append(String.format("- %s\n", product.getName()));
        });

        body.append("\nWe appreciate you!\nBest regards,\nms-project");

        helper.setFrom("ms-project@gmail.com");
        helper.setTo(orderConfirmation.getCustomer().getUsername());
        helper.setSubject(subject);
        helper.setText(body.toString());

        return mimeMessage;
    }

    private MimeMessage createProductStockNotificationEmail(ProductStockNotification productStockNotification) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        String subject = String.format("Stock Alert: %s is running low", productStockNotification.getName());
        StringBuilder body = new StringBuilder();
        body.append(String.format("Dear User,\n\n"));
        body.append(String.format("The stock for the product '%s' is running low.\n\n", productStockNotification.getName()));
        body.append(String.format("Product ID: %s\n", productStockNotification.getProductId()));
        body.append(String.format("Available Quantity: %.2f\n", productStockNotification.getAvailableQuantity()));
        body.append(String.format("Category: %s\n\n", productStockNotification.getCategoryName()));
        body.append("Please consider restocking it soon.\n");
        body.append("Best regards,\nInventory Management System");

        helper.setFrom("ms-project@gmail.com");
        helper.setTo("ms-project@gmail.com");
        helper.setSubject(subject);
        helper.setText(body.toString());

        return mimeMessage;
    }
}
