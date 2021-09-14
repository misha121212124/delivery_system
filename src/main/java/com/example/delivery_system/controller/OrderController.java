package com.example.delivery_system.controller;

import com.example.delivery_system.dto.CarriageDto;
import com.example.delivery_system.dto.GoodDto;
import com.example.delivery_system.dto.OrderDto;
import com.example.delivery_system.dto.OutletDto;
import com.example.delivery_system.entity.Order;
import com.example.delivery_system.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;


    @GetMapping()
    public ResponseEntity<List<OrderDto>> allOrders() {
        List<Order> orders = orderService.allOrders();
        return ResponseEntity.ok(orders.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    ResponseEntity<OrderDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(convertToDto(orderService.findOrderById(id)));
    }

    @GetMapping("/carriages/{id}")
    ResponseEntity<List<CarriageDto>> getCarriagesByOrderId(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOrderById(id)
                .getCarriages().stream()
                .map(carriage -> modelMapper.map(carriage, CarriageDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/outlets/{id}")
    ResponseEntity<OutletDto> getOutletByOrderId(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(orderService.findOrderById(id).getOutlet(), OutletDto.class));
    }

    @GetMapping("/goods/{id}")
    ResponseEntity<GoodDto> getGoodByOrderId(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(orderService.findOrderById(id).getGood(), GoodDto.class));
    }

    @PostMapping()
    ResponseEntity<OrderDto> newOrder(@RequestBody OrderDto newOrder){
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(orderService.save(convertToEntity(newOrder))));
    }

    @PutMapping("/{id}")
    ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto newOrder, @PathVariable Long id){
        return ResponseEntity.ok(convertToDto(orderService.update(convertToEntity(newOrder), id)));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    private OrderDto convertToDto(Order order) {
        OrderDto orderDto = modelMapper.map(order, OrderDto.class);
        return orderDto;
    }

    private Order convertToEntity(OrderDto orderDto){
        Order order = modelMapper.map(orderDto, Order.class);
        return order;
    }
}
