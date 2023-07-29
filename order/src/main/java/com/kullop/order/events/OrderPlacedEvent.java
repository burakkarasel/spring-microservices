package com.kullop.order.events;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderPlacedEvent {
    private String product;
    private Long orderId;
    private Double price;
    private Integer quantity;
}
