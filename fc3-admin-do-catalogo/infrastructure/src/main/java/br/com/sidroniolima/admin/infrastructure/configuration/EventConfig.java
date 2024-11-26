package br.com.sidroniolima.admin.infrastructure.configuration;

import br.com.sidroniolima.admin.infrastructure.configuration.annotations.VideoCreatedQueue;
import br.com.sidroniolima.admin.infrastructure.configuration.properties.amqp.QueueProperties;
import br.com.sidroniolima.admin.infrastructure.services.EventService;
import br.com.sidroniolima.admin.infrastructure.services.impl.RabbitEventService;
import org.springframework.amqp.rabbit.core.RabbitOperations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {

    @Bean
    @VideoCreatedQueue
    EventService videoCreatedEventService(
            @VideoCreatedQueue final QueueProperties props,
            final RabbitOperations ops
    ) {
        return new RabbitEventService(props.getExchange(), props.getRoutingKey(), ops);
    }
}
