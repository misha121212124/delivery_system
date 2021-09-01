package com.example.delivery_system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "orders")
public class Order implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "count", nullable = false)
    private int count;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "outlet_id")
    private Outlet outlet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "good_id")
    private Good good;

//    @Transient
    @ManyToMany(mappedBy = "orders")///???
    private Set<Carriage> carriages;

//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
//    private Set<OrdersOnCarriage> ordersOnCarriageSet;

}
