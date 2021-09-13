package com.example.delivery_system.controller;

import com.example.delivery_system.dto.GoodDto;
import com.example.delivery_system.dto.OrderDto;
import com.example.delivery_system.dto.OutletDto;
import com.example.delivery_system.entity.Carriage;
import com.example.delivery_system.entity.Good;
import com.example.delivery_system.entity.Order;
import com.example.delivery_system.entity.Outlet;
import com.example.delivery_system.repository.GoodRepository;
import com.example.delivery_system.repository.OrderRepository;
import com.example.delivery_system.repository.OutletRepository;
import com.example.delivery_system.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {
    @Mock
    OutletRepository outletRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    GoodRepository goodRepository;

    OrderController orderController;

    @BeforeEach
    void setUp() {
        orderController = new OrderController(new OrderService(outletRepository,orderRepository,goodRepository), new ModelMapper());
    }

    @Test
    void allOrders() {
        List<Order> list = new ArrayList<>();
        Order testOrder = new Order();
        testOrder.setCarriages(new HashSet<>());
        testOrder.setOutlet(new Outlet());
        testOrder.setGood(new Good());
        list.add(testOrder);
        when(orderRepository.findAll(any(org.springframework.data.domain.Sort.class))).thenReturn(list);
        ResponseEntity<List<OrderDto>> listDto = orderController.allOrders();
        System.out.println(listDto);
        assertNotNull(listDto);
    }

    @Test
    void getById() {
        Order testOrder = new Order();
        testOrder.setCarriages(new HashSet<>());
        testOrder.setOutlet(new Outlet());
        testOrder.setGood(new Good());
        when(orderRepository.findById(1L)).thenReturn(Optional.of(testOrder));
        ResponseEntity<OrderDto> order = orderController.getById(1L);
        System.out.println(order);
        assertNotNull(order);
    }

    @Test
    void newOrder() throws ParseException {
        OrderDto orderDto = new OrderDto();
        orderDto.setCarriages(new HashSet<>());
        orderDto.setOutlet(new OutletDto());
        orderDto.setGood(new GoodDto());
        Order forSave = new Order();
        forSave.setCarriages(new HashSet<>());
        forSave.setOutlet(new Outlet());
        forSave.setGood(new Good());
        when(orderRepository.save(any(Order.class))).thenReturn(forSave);
        ResponseEntity<OrderDto> savedOrder = orderController.newOrder(orderDto);
        System.out.println(savedOrder);
        assertNotNull(savedOrder);
    }

    @Test
    void updateOrder() throws ParseException {
        OrderDto orderDto = new OrderDto();
        orderDto.setCarriages(new HashSet<>());
        orderDto.setOutlet(new OutletDto());
        orderDto.setGood(new GoodDto());
        orderDto.setId(6L);
        Order forSave = new Order();
        forSave.setCarriages(new HashSet<>());
        forSave.setOutlet(new Outlet());
        forSave.setGood(new Good());
        when(orderRepository.save(any(Order.class))).thenReturn(forSave);
        when(orderRepository.findById(6L)).thenReturn(Optional.of(new Order())
                .map(order -> {
                    order.setId(6L);
                    return order;
                }));
        ResponseEntity<OrderDto> savedOrder = orderController.updateOrder(orderDto,6L);
        System.out.println(savedOrder);
        assertNotNull(savedOrder);
    }

    @Test
    void deleteOrder() {
        ResponseEntity<Void> delResult = orderController.deleteOrder(1L);
        System.out.println(delResult);
        assertNotNull(delResult);
    }
}