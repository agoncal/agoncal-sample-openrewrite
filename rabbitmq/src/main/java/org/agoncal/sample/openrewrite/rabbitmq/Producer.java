package org.agoncal.sample.openrewrite.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private RabbitTemplate template;

    public void sendMessage(String queueName, String message) {
        try {
            template.send(queueName, new Message(message.getBytes()));
            logger.info("Sent message to queue {}: {}", queueName, message);
        } catch (Exception e) {
            logger.error("Failed to send message to queue {}: {}", queueName, message, e);
        }
    }
}
