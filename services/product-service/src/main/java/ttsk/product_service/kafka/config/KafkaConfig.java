package ttsk.product_service.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic productStockNotification() {
        return new NewTopic("productStockNotification", 1, (short) 1);
    }
}
