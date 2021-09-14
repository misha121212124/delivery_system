package com.example.delivery_system.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "outlets")
public class Outlet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;


    @OneToMany(mappedBy = "outlet", cascade = CascadeType.ALL)
    private List<GoodsInOutlets> goodsInOutletsSet;

    @OneToMany(mappedBy = "outlet_from", cascade = CascadeType.ALL)
    private List<Route> routesFrom;

    @OneToMany(mappedBy = "outlet_to", cascade = CascadeType.ALL)
    private List<Route> routesTo;

    @OneToMany(mappedBy = "outlet", cascade = CascadeType.ALL)
    private List<Order> orderList;

}
