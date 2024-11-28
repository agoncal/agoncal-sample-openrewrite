package org.agoncal.sample.openrewrite.rabbitmqjson;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ApplicationRabbitMQJson {

    public static final String FOO_QUEUE = "foo";
    public static final String BAR_QUEUE = "bar";

    public static void main(String[] args) {
        SpringApplication.run(ApplicationRabbitMQJson.class, args);
    }

    @Bean
    public RabbitTemplate messagingTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonConverter());
        return template;
    }

    @Bean
    public MessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
	