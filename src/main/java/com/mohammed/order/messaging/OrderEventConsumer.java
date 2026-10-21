package com.mohammed.order.messaging;

import com.mohammed.order.messaging.event.OrderCreatedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Incoming;

//@ApplicationScoped
public class OrderEventConsumer {
    @Incoming("order-events-in")
    public void consume(OrderCreatedEvent event) {
        System.out.println(
                "Received order: " + event.orderId()
                        + " product: " + event.product()
        );
    }
}
