package ttsk.notification_service.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductStockNotification {
    private UUID productId;
    private String name;
    private double availableQuantity;
    private String categoryName;
}
