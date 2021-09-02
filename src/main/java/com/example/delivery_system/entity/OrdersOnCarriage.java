//package com.example.delivery_system.entity;
//
//import lombok.Data;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.util.Objects;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Entity
//@Table(name = "carriages_orders")
//public class OrdersOnCarriage implements Serializable {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "order_id")
//    private Order order;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "carriage_id")
//    private Carriage carriage;
//
//    public OrdersOnCarriage(Order order, Carriage carriage) {
//        this.order = order;
//        this.carriage = carriage;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof OrdersOnCarriage)) return false;
//        OrdersOnCarriage that = (OrdersOnCarriage) o;
//        return Objects.equals(order.getId(), that.order.getId()) &&
//                Objects.equals(carriage.getId(), that.carriage.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(order.getId(), carriage.getId());
//    }
//}
