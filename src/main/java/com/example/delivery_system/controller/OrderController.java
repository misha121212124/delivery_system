package com.example.delivery_system.controller;

import com.example.delivery_system.dto.OrderDto;
import com.example.delivery_system.entity.Order;
import com.example.delivery_system.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class OrderController {
    private final OrderService orderService;

    private final ModelMapper modelMapper;


    @GetMapping("/orders")
    public ResponseEntity<List<OrderDto>> allOrders() {
        List<Order> orders = orderService.allOrders();
        return ResponseEntity.ok(orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/orders/{id}")
    ResponseEntity<OrderDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(convertToDto(orderService.findOrderById(id)));
    }

    @PostMapping("/order")
    ResponseEntity<OrderDto> newOrder(@RequestBody OrderDto newOrder) throws ParseException {
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(orderService.save(convertToEntity(newOrder))));
    }

    @PutMapping("/orders/{id}")
    ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto newOrder, @PathVariable Long id) throws ParseException {
        return ResponseEntity.ok(convertToDto(orderService.update(convertToEntity(newOrder), id)));
    }

    @DeleteMapping("/orders/{id}")
    ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    private OrderDto convertToDto(Order order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        return orderDto;
    }

    private Order convertToEntity(OrderDto orderDto) throws ParseException {
        Order order = modelMapper.map(orderDto, Order.class);
        return order;
    }
}
