package ttsk.api_gateway.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.WebFilter;
import ttsk.api_gateway.security.JwtGatewayFilter;

@Configuration
public class GatewayConfig {
    @Bean
    @LoadBalanced
    public WebClient.Builder loadBalancedWebClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebFilter jwtFilter(JwtGatewayFilter jwtGatewayFilter) {
        return jwtGatewayFilter;
    }
}