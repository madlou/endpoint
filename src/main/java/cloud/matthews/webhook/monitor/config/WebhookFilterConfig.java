package cloud.matthews.webhook.monitor.config;

import cloud.matthews.webhook.monitor.filter.WebhookLoggingFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import jakarta.servlet.Filter;

/**
 * Configuration class to register the webhook logging filter.
 */
@Configuration
public class WebhookFilterConfig {

    @Bean
    @Order(1)
    public Filter webhookLoggingFilter() {
        return new WebhookLoggingFilter();
    }
}
