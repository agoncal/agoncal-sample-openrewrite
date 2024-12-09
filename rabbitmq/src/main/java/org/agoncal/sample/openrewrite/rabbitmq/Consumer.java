package org.agoncal.sample.openrewrite.rabbitmq;

import static org.agoncal.sample.openrewrite.rabbitmq.ApplicationRabbitMQ.BAR_QUEUE;
import static org.agoncal.sample.openrewrite.rabbitmq.ApplicationRabbitMQ.FOO_QUEUE;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Consumer {

    private static final Logger logger = LoggerFactory.getLogger(Consumer.class);

    @RabbitListener(queues = FOO_QUEUE)
    public void listenToFoo(String message) {
        logger.info("Message {} received on queue {}", message, FOO_QUEUE);
    }

    @RabbitListener(queues = BAR_QUEUE)
    public void listenToBar(String message) {
        logger.info("Message {} received on queue {}", message, BAR_QUEUE);
    }
}

