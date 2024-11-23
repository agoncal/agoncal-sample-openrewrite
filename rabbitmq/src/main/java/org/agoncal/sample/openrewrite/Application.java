package org.agoncal.sample.openrewrite;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

    public static final String FOO_QUEUE = "foo";
    public static final String BAR_QUEUE = "bar";

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        return new RabbitTemplate(connectionFactory);
    }

    @Bean
    public Queue foo() {
        return new Queue(FOO_QUEUE);
    }

    @Bean
    public Queue bar() {
        return new Queue(BAR_QUEUE);
    }
}
	