package com.example.delivery_system.service;

import com.example.delivery_system.entity.Order;
import com.example.delivery_system.entity.Outlet;
import com.example.delivery_system.repository.GoodRepository;
import com.example.delivery_system.repository.OrderRepository;
import com.example.delivery_system.repository.OutletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @Mock
    OutletRepository outletRepository;
    @Mock
    OrderRepository orderRepository;
    @Mock
    GoodRepository goodRepository;

    OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderService(outletRepository,orderRepository,goodRepository);
    }

    @Test
    void findOrderById() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(new Order()));
        Order order = orderService.findOrderById(1L);
        assertNotNull(order);
    }

    @Test
    void update() {
        Order updateOrder = new Order();
        updateOrder.setId(8L);
        updateOrder.setCount(2);
        when(orderRepository.save(any(Order.class))).thenReturn(updateOrder);
        when(orderRepository.findById(8L)).thenReturn(Optional.of(new Order())
                .map(order -> {
                    order.setId(8L);
                    return order;
                }));
        updateOrder = orderService.update(updateOrder, 8L);
        assertNotNull(updateOrder);
    }

    @Test
    void allOrders() {
        List<Order> list = new ArrayList<>();
        list.add(new Order());
        when(orderRepository.findAll(any(org.springframework.data.domain.Sort.class))).thenReturn(list);
        List orders = orderService.allOrders();
        assertNotNull(orders);
    }

    @Test
    void save() {
        Order saveOrder = new Order();
        saveOrder.setId(10L);
        saveOrder.setCount(3);
        when(orderRepository.save(any(Order.class))).thenReturn(saveOrder);
        saveOrder = orderService.save(saveOrder);
        assertNotNull(saveOrder);
    }

    @Test
    void deleteById() {// what should I do with it?
        orderService.deleteById(2L);
        assert true;
    }
}