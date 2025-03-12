package ttsk.api_gateway.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ttsk.api_gateway.exception.UnauthorizedException;
import ttsk.api_gateway.security.TokenResponse;

@Service
public class AuthClient {

    private final WebClient webClient;

    public AuthClient(WebClient.Builder webClientBuilder,
                      @Value("${application.config.auth-url}") String refreshUrl) {
        this.webClient = webClientBuilder.baseUrl(refreshUrl).build();

    }

    public Mono<TokenResponse> refreshToken(String refreshToken) {
        return webClient.post()
                .uri("/refresh")
                .bodyValue(refreshToken)
                .retrieve()
                .bodyToMono(TokenResponse.class)
                .onErrorResume(error -> Mono.error(new UnauthorizedException("Failed to refresh JWT token")));
    }
}
