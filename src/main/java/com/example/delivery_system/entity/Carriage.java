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
    @Column(name = "id")
    private Long id;

    @Column(name = "capacity", nullable = false)
    private int capacity;

    @Column(name = "point", nullable = false)
    private byte point;/////!!!!!

    @OneToMany(mappedBy = "carriage", cascade = CascadeType.ALL)
    private Set<RoutesForCarriage> routesForCarriageSet;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Order> orders;

}
