package com.example.delivery_system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "goods")
public class Good implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;

    @Column(/*name = "name",*/ unique = true, nullable = false)
    private String name;

    @Column(/*name = "volume",*/ nullable = false)
    private int volume;

    @OneToMany(mappedBy = "good", cascade = CascadeType.ALL)
    private List<GoodsInOutlets> goodsInOutlets;

    @OneToMany(mappedBy = "good", cascade = CascadeType.ALL)
    private List<Order> orderList;

//    public Good(String name, int volume) {
//        this.name = name;
//        this.volume = volume;
//    }
//
//    public Good() {
//
//    }
}
