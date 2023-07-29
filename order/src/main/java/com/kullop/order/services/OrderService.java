package com.kullop.order.services;

import com.kullop.order.clients.InventoryClient;
import com.kullop.order.entities.Order;
import com.kullop.order.events.OrderPlacedEvent;
import com.kullop.order.repositories.OrderRepository;
import com.kullop.order.requests.PlaceOrderRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final KafkaTemplate kafkaTemplate;
    private final OrderRepository orderRepository;

    private final InventoryClient inventoryClient;

    public Order placeOrder(PlaceOrderRequest request) {

        if(!this.inventoryClient.exists(request.getProduct()).getExists()){
            throw new EntityNotFoundException("Product is not available");
        }

        Order saved = this.orderRepository.save(
                Order.builder()
                        .product(request.getProduct())
                        .price(request.getPrice())
                        .quantity(request.getQuantity())
                        .shipped("PLACED")
                        .build()
        );

        this.kafkaTemplate.send("prod.orders.placed", String.valueOf(saved.getId()), OrderPlacedEvent.builder()
                .product(request.getProduct())
                .price(request.getPrice())
                .quantity(request.getQuantity())
                .orderId(saved.getId())
                .build());

        return saved;
    }

    @KafkaListener(topics = "prod.orders.shipped", groupId = "order-group")
    public void handleOrderShippedEvent(String orderId) {
        this.orderRepository.findById(Long.valueOf(orderId)).ifPresent(o -> {
            o.setShipped("SHIPPED");
            this.orderRepository.save(o);
        }
        );
        System.out.println("Order Updated");
    }

    public Order getOrderById(Long orderId) {
        return this.orderRepository.findById(orderId).orElse(null);
    }
}
