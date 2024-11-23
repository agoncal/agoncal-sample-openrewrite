package org.agoncal.sample.openrewrite.rabbitmq;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApplicationRabbitMQ {

    public static final String FOO_QUEUE = "foo";
    public static final String BAR_QUEUE = "bar";

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRabbitMQ.class, args);
    }

    @Bean
    public RabbitTemplate messagingTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }
}
	