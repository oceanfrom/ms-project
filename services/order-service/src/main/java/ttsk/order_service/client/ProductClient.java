package ttsk.order_service.client;

import jakarta.ws.rs.core.HttpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ttsk.order_service.dto.PurchaseRequest;
import ttsk.order_service.dto.ProductPurchaseResponse;
import ttsk.order_service.exception.OrderProcessException;
import ttsk.order_service.security.JwtTokenProvider;

import java.util.List;

@Service
public class ProductClient {

    private final WebClient webClient;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public ProductClient(WebClient.Builder webClientBuilder,
                         @Value("${application.config.product-url}") String productUrl, JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.webClient = webClientBuilder.baseUrl(productUrl).build();
    }

    public List<ProductPurchaseResponse> purchaseProducts(List<PurchaseRequest> purchaseRequests) {
        String accessToken = jwtTokenProvider.getAccessToken();
        String refreshToken = jwtTokenProvider.getRefreshToken();


        return webClient.post()
                .uri("/purchase")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .header("Refresh-Token", refreshToken)
                .bodyValue(purchaseRequests)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<ProductPurchaseResponse>>() {})
                .doOnError(error -> {
                    throw new OrderProcessException("An error occurred while processing the products purchase: " + error.getMessage());
                })
                .block();
    }


}
