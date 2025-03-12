package ttsk.api_gateway.discovery;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class DiscoveryChecker {
    private final DiscoveryClient discoveryClient;

    public DiscoveryChecker(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    public boolean isServiceAvailable(String serviceName) {
        List<String> services = discoveryClient.getServices();
        boolean isAvailable = services.contains(serviceName);

        if (!isAvailable) {
            log.info("Service {} is unavailable.", serviceName);
        }
        return isAvailable;
    }
}
