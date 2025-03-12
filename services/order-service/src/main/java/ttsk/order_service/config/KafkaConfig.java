package ttsk.order_service.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic orderTopic() {
        return new NewTopic("orderTopic", 1, (short) 1);
    }
}
