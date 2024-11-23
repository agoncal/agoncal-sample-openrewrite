package org.agoncal.sample.openrewrite.rabbitmq;

import static org.agoncal.sample.openrewrite.rabbitmq.ApplicationRabbitMQ.BAR_QUEUE;
import static org.agoncal.sample.openrewrite.rabbitmq.ApplicationRabbitMQ.FOO_QUEUE;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Consumer {

    private volatile CountDownLatch latch = new CountDownLatch(2);

    @RabbitListener(queues = FOO_QUEUE)
    public void listenToFoo(String msg) {
        System.out.println("Message received on queue " + FOO_QUEUE + " " + msg);
        this.latch.countDown();
    }

    @RabbitListener(queues = BAR_QUEUE)
    public void listenToBar(String msg) {
        System.out.println("Message received on queue " + BAR_QUEUE + " " + msg);
        this.latch.countDown();
    }
}

