package com.example.delivery_system.controller;

import com.example.delivery_system.dto.CarriageDto;
import com.example.delivery_system.dto.GoodDto;
import com.example.delivery_system.dto.OrderDto;
import com.example.delivery_system.dto.OutletDto;
import com.example.delivery_system.entity.Order;
import com.example.delivery_system.service.DtoService;
import com.example.delivery_system.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final DtoService dtoService;

    @GetMapping()
    public ResponseEntity<List<OrderDto>> allOrders() {
        List<Order> orders = orderService.allOrders();
        return ResponseEntity.ok(orders.stream()
                .map(dtoService::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    ResponseEntity<OrderDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(dtoService.convertToDto(orderService.findOrderById(id)));
    }

    @GetMapping("/carriages/{id}")
    ResponseEntity<List<CarriageDto>> getCarriagesByOrderId(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id)
                .getCarriages().stream()
                .map(dtoService::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/outlets/{id}")
    ResponseEntity<OutletDto> getOutletByOrderId(@PathVariable Long id) {
        return ResponseEntity.ok(dtoService.convertToDto(orderService.findOrderById(id).getOutlet()));
    }

    @GetMapping("/goods/{id}")
    ResponseEntity<GoodDto> getGoodByOrderId(@PathVariable Long id) {
        return ResponseEntity.ok(dtoService.convertToDto(orderService.findOrderById(id).getGood()));
    }

    @PostMapping()
    ResponseEntity<OrderDto> newOrder(@RequestBody OrderDto newOrder){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dtoService.convertToDto(orderService.save(dtoService.convertToEntity(newOrder))));
    }

    @PutMapping("/{id}")
    ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto newOrder, @PathVariable Long id){
        return ResponseEntity.ok(dtoService.convertToDto(orderService.update(dtoService.convertToEntity(newOrder), id)));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();

    }
}
