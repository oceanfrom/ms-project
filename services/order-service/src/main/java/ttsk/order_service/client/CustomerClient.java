package ttsk.order_service.client;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ttsk.order_service.dto.CustomerResponse;
import ttsk.order_service.exception.OrderProcessException;
import ttsk.order_service.security.JwtTokenProvider;

import java.util.Optional;
import java.util.UUID;

@Component
@Slf4j
public class CustomerClient {

    private final WebClient webClient;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public CustomerClient(WebClient.Builder webClientBuilder,
                          @Value("${application.config.customer-url}") String customerUrl, JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.webClient = webClientBuilder.baseUrl(customerUrl).build();
    }

    public Optional<CustomerResponse> findCustomerById(UUID customerId) {
        log.info("Sending request to /api/v1/customers/{} ", customerId);

        String accessToken = jwtTokenProvider.getAccessToken();
        String refreshToken = jwtTokenProvider.getRefreshToken();

        return webClient.get()
                .uri("/{customer-id}", customerId.toString())
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header("Refresh-Token", refreshToken)
                .retrieve()
                .bodyToMono(CustomerResponse.class)
                .map(Optional::of)
                .onErrorReturn(Optional.empty())
                .doOnError(error -> {
                    throw new OrderProcessException("An error occurred while finding customer by id: " + error.getMessage());
                })
                .block();
    }
}
