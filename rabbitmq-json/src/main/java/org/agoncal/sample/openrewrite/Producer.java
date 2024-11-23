package org.agoncal.sample.openrewrite;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class Producer {

    private static final Logger logger = LoggerFactory.getLogger(Producer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMessage(String queueName, String message) {
        String json = "{\"" + queueName + "\" : \"" + message + "\" }";
        Message jsonMessage = MessageBuilder.withBody(json.getBytes())
                .andProperties(MessagePropertiesBuilder.newInstance().setContentType("application/json").build())
                .build();
        try {
            jsonMessage.getMessageProperties().setHeader("__TypeId__", queueName);
            rabbitTemplate.convertAndSend(queueName, jsonMessage);
            logger.info("Sent message to queue {}: {}", queueName, jsonMessage);
        } catch (Exception e) {
            logger.error("Failed to send message to queue {}: {}", queueName, jsonMessage, e);
        }
    }
}