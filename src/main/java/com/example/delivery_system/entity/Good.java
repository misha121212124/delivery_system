package com.example.delivery_system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name = "goods")
public class Good implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private int volume;

    @OneToMany(mappedBy = "good", cascade = CascadeType.ALL)
    private List<GoodsInOutlets> goodsInOutlets;

    @OneToMany(mappedBy = "good", cascade = CascadeType.ALL)
    private List<Order> orderList;

}