package org.agoncal.sample.openrewrite.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Consumer {

    private volatile CountDownLatch latch = new CountDownLatch(2);

    @RabbitListener(queues = ApplicationRabbitMQ.FOO_QUEUE)
    public void listenForAFoo(String msg) {
        System.out.println("Message received on queue " + ApplicationRabbitMQ.FOO_QUEUE + " " + msg);
        this.latch.countDown();
    }

    @RabbitListener(queues = ApplicationRabbitMQ.BAR_QUEUE)
    public void listenForABar(String msg) {
        System.out.println("Message received on queue " + ApplicationRabbitMQ.BAR_QUEUE + " " + msg);
        this.latch.countDown();
    }
}

