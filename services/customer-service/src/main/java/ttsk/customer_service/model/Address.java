package ttsk.customer_service.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class Address {
    @NotNull(message = "City is required")
    private String city;
    @NotNull(message = "Street is required")
    private String street;
    @NotNull(message = "houseNumber is required")
    private String houseNumber;
    @NotNull(message = "Postal code is required")
    private String postalCode;
}
