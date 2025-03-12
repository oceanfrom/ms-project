package ttsk.notification_service.model;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ProductPurchase {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private String categoryName;
}
