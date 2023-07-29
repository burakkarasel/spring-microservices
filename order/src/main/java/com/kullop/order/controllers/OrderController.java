package com.kullop.order.controllers;

import com.kullop.order.entities.Order;
import com.kullop.order.requests.PlaceOrderRequest;
import com.kullop.order.services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order placeOrder(@RequestBody PlaceOrderRequest request) {
        return this.orderService.placeOrder(request);
    }

    @GetMapping("/{orderId}")
    public Order getOrderById(@PathVariable Long orderId){
        return this.orderService.getOrderById(orderId);
    }
}
