package ttsk.api_gateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.ws.rs.core.HttpHeaders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;
import ttsk.api_gateway.client.AuthClient;

import java.nio.file.AccessDeniedException;
import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class JwtTokenProvider {
    private final Key key;
    private final AuthClient authClient;

    public JwtTokenProvider(JwtProperties jwtProperties, AuthClient authClient) {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
        this.authClient = authClient;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String extractTokenFromRequest(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    public Mono<Void> refreshAccessToken(ServerWebExchange exchange, WebFilterChain chain) {
        String refreshToken = extractRefreshTokenFromRequest(exchange);
        log.info("REFRESH TOKEN TO AUTH: {}", refreshToken);
        if (refreshToken == null) {
            return Mono.error(new AccessDeniedException("JWT Token is invalid and no refresh token found"));
        }

        log.info("Refreshing access token using refresh token...");
        return authClient.refreshToken(refreshToken.trim())
                .flatMap(response -> {
                    String newAccessToken = response.accessToken();
                    log.info("Received new access token: {}", newAccessToken);
                    ServerWebExchange mutatedExchange = exchange.mutate()
                            .request(r -> r.header(HttpHeaders.AUTHORIZATION, "Bearer " + newAccessToken))
                            .build();

                    return chain.filter(mutatedExchange);
                })
                .doOnError(error -> log.error("Failed to refresh JWT token: {}", error.getMessage()));
    }

    public String extractRefreshTokenFromRequest(ServerWebExchange exchange) {
        String refreshToken = exchange.getRequest().getHeaders().getFirst("Refresh-Token");
        if (refreshToken == null) {
            log.info("Refresh Token is not present in the request headers.");
        }

        return refreshToken;
    }
}
