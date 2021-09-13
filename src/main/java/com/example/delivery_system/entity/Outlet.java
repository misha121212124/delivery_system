package com.example.delivery_system.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "outlets")
public class Outlet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(/*name = "name",*/ unique = true, nullable = false)
    private String name;


    @OneToMany(mappedBy = "outlet", cascade = CascadeType.ALL)////розібратися, чому з mappedBy = "outlets" виникає помилка
    private List<GoodsInOutlets> goodsInOutletsSet;
}

