package com.example.delivery_system.controller;

import com.example.delivery_system.entity.Order;
import com.example.delivery_system.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class OrderController {
    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/orders")
    public List<Order> allOrders() {
        return orderService.allOrders();
    }

    @GetMapping("/orders/{id}")
    Order getById(@PathVariable Long id) {
        return orderService.findOrderById(id);
    }

    @PostMapping("/order")
    Order newOrder(@RequestBody Order newOrder) {
        return orderService.save(newOrder);
    }

    @PutMapping("/orders/{id}")
    Order updateOrder(@RequestBody Order newOrder, @PathVariable Long id) {
        return orderService.update(newOrder, id);
    }

    @DeleteMapping("/orders/{id}")
    void deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
    }
}
