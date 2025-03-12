package ttsk.auth_service.model;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
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
