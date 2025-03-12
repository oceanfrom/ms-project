package ttsk.order_service.exception;

public class OrderProcessException extends RuntimeException {
    public OrderProcessException(String message) {
        super(message);
    }
}
