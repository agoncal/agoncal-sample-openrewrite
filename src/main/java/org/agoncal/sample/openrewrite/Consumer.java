package org.agoncal.sample.openrewrite;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class Consumer {

    private volatile CountDownLatch latch = new CountDownLatch(2);

    @RabbitListener(queues = Application.FOO_QUEUE)
    public void listenForAFoo(Foo foo) {
        System.out.println("Expected a Foo, got a " + foo);
        this.latch.countDown();
    }

    @RabbitListener(queues = Application.BAR_QUEUE)
    public void listenForABar(Bar bar) {
        System.out.println("Expected a Bar, got a " + bar);
        this.latch.countDown();
    }

    public static class Foo {

        private String foo;

        public Foo() {
            super();
        }

        public Foo(String foo) {
            this.foo = foo;
        }

        public String getFoo() {
            return this.foo;
        }

        public void setFoo(String foo) {
            this.foo = foo;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + " [foo=" + this.foo + "]";
        }

    }

    public static class Bar {

        private String bar;

        public Bar() {
            super();
        }

        public Bar(String bar) {
            this.bar = bar;
        }

        public String getBar() {
            return this.bar;
        }

        public void setBar(String bar) {
            this.bar = bar;
        }

        @Override
        public String toString() {
            return getClass().getSimpleName() + " [bar=" + this.bar + "]";
        }

    }
}

