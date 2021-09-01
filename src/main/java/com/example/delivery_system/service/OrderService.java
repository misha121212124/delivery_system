package com.example.delivery_system.service;

import com.example.delivery_system.entity.Carriage;
import com.example.delivery_system.entity.Order;
import com.example.delivery_system.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OutletRepository outletRepository;
    private final OrderRepository orderRepository;
    private final GoodRepository goodRepository;

    public Order findOrderById(Long orderId) {
        Optional<Order> orderFromDb = orderRepository.findById(orderId);
        return orderFromDb.orElse(new Order());
    }

    public Order update(Order newOrder, Long oldId) {
        return orderRepository.findById(oldId).
                map(order -> {
                    order.setCount(newOrder.getCount());
                    order.setGood(newOrder.getGood());
                    order.setOutlet(newOrder.getOutlet());
//                    order.setOrdersOnCarriageSet(newOrder.getOrdersOnCarriageSet());
                    order.setCarriages(newOrder.getCarriages());
                    //??
//                    List<Carriage> diff = new ArrayList<Carriage>(order.getCarriages());
//                    diff.removeAll(newOrder.getCarriages());
//                    for (Carriage temp: diff){
//                        Set<Order> orderList = new HashSet<>(temp.getOrders());
//                        orderList.add(order);
//                        temp.setOrders(orderList);
//                        carriageRepository.save(temp);
//                    }
                    return orderRepository.save(order);
                })
                .orElseGet(() -> {
                    newOrder.setId(oldId);
                    return orderRepository.save(newOrder);
                });
    }

    public List<Order> allOrders() {
        return orderRepository.findAll(Sort.by("count"));
    }


    public Order save(Order newOrder){
        return orderRepository.save(newOrder);
    }

    public void deleteById(long orderId){
        orderRepository.deleteById(orderId);
    }
}
