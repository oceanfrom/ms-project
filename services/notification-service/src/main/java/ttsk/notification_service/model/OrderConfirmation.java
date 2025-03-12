package ttsk.notification_service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class OrderConfirmation {
    @JsonProperty("customerResponse")
    private Customer customer;
    private String orderReference;
    private BigDecimal amount;
    private PaymentMethod paymentMethod;
    private List<ProductPurchase> products;
}

