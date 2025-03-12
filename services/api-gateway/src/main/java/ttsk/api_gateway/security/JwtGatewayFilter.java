package ttsk.api_gateway.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.nio.file.AccessDeniedException;

@Component
@Slf4j
public class JwtGatewayFilter implements WebFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtGatewayFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        log.info("Request path: {}", path);

        if (path.startsWith("/eureka") || path.startsWith("/api/v1/auth")) {
            return chain.filter(exchange);
        }

        String accessToken = jwtTokenProvider.extractTokenFromRequest(exchange);
        if (accessToken == null) {
            log.warn("No JWT Token found in request headers.");
            return Mono.error(new AccessDeniedException("No JWT Token found"));
        }

        if (jwtTokenProvider.validateToken(accessToken)) {
            log.info("JWT Token is valid.");
            return chain.filter(exchange);
        }

        log.info("JWT Token is invalid. Attempting to refresh using refresh accessToken.");
        return jwtTokenProvider.refreshAccessToken(exchange, chain);
    }
}
