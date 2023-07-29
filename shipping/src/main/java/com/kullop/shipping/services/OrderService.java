package com.kullop.shipping.services;

import com.kullop.shipping.entities.Shipping;
import com.kullop.shipping.events.OrderPlacedEvent;
import com.kullop.shipping.repositories.ShippingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final KafkaTemplate kafkaTemplate;
    private final ShippingRepository shippingRepository;

    @KafkaListener(topics = "prod.orders.placed", groupId = "shipping-group")
    public void handleOrderPlacedEvent(OrderPlacedEvent event) {
        Shipping saved = this.shippingRepository.save(
                Shipping.builder()
                        .orderId(event.getOrderId())
                        .build());
        this.kafkaTemplate.send("prod.orders.shipped", String.valueOf(saved.getOrderId()), String.valueOf(saved.getOrderId()));
    }
}
