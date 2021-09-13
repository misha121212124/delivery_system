package com.example.delivery_system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "carriages")
public class Carriage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;

    @Column(/*name = "capacity",*/ nullable = false)
    private int capacity;

    @Column(/*name = "point",*/ nullable = false)
    private int point;

    @OneToMany(mappedBy = "carriage", cascade = CascadeType.ALL)
    private Set<RoutesForCarriage> routesForCarriageSet;

    @ManyToMany///апдейтиити треба carriage.setOrders();, не можна order.setCarriages();
    @JoinTable(
            name = "carriages_orders",
            joinColumns = @JoinColumn(name = "carriage_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private Set<Order> orders;

//    @OneToMany(mappedBy = "carriage", cascade = CascadeType.ALL)
//    private Set<OrdersOnCarriage> ordersOnCarriageSet;


}
