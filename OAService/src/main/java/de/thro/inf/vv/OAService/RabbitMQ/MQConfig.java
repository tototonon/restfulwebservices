package de.thro.inf.vv.OAService.RabbitMQ;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
@Configuration
public class MQConfig {

    private static final boolean NON_DURABLE = false;

    public final static String APPROVED_CUSTOMERS = "approvedCustomers";
    public final static String DECLINED_CUSTOMERS = "declinedCustomers";
    public final static String EXCHANGE_NAME = "exchanger";

    public final static String BINDING_PATTERN_IMPORTANT = "*.important.*";
    public final static String BINDING_PATTERN_ERROR = "#.error";

    @Bean
    public Declarables topicBindings() {
        Queue approved = new Queue(APPROVED_CUSTOMERS, NON_DURABLE);
        Queue declined = new Queue(DECLINED_CUSTOMERS, NON_DURABLE);

        TopicExchange topicExchange = new TopicExchange(EXCHANGE_NAME, NON_DURABLE, false);

        return new Declarables(approved, declined, topicExchange, BindingBuilder
                .bind(approved)
                .to(topicExchange)
                .with(BINDING_PATTERN_IMPORTANT), BindingBuilder
                .bind(declined)
                .to(topicExchange)
                .with(BINDING_PATTERN_ERROR));
    }
}
*/