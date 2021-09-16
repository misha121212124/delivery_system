package com.example.delivery_system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "carriages")
public class Carriage implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int capacity;

    @Column(nullable = false)
    private int point;

    @OneToMany(mappedBy = "carriage", cascade = CascadeType.ALL)
    private List<RoutesForCarriage> routesForCarriage;

    @ManyToMany///апдейтиити треба carriage.setOrders();, не можна order.setCarriages();
    @JoinTable(
            name = "carriages_orders",
            joinColumns = @JoinColumn(name = "carriage_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orders;
}
