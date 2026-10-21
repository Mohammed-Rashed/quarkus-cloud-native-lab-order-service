package com.mohammed.order.messaging;

import com.mohammed.order.messaging.event.OrderCreatedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import io.smallrye.reactive.messaging.kafka.Record;

@ApplicationScoped
public class OrderEventProducer {

    @Channel("order-events-out")
    Emitter<Record<String, OrderCreatedEvent>> emitter;
    public void send(OrderCreatedEvent event) {
        System.out.println("Sending Kafka event: " + event);

        emitter.send(
                Record.of(
                        event.orderId().toString(),
                        event
                )
        ).whenComplete((success, failure) -> {

            if (failure != null) {
                System.err.println("Kafka send FAILED: " + failure.getMessage());
                failure.printStackTrace();
            } else {
                System.out.println("Kafka send SUCCESS");
            }

        });
    }
}
