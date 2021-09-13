package com.example.delivery_system.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@Entity
@Table(name = "routes")
public class Route implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "id")
    private Long id;

//    @Column(name = "distance", nullable = false)
    private float distance;

    //////////////???????????????//////////////
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "outlet_from_id")
    private Outlet outlet_from;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "outlet_to_id")
    private Outlet outlet_to;
    //////////////////////////////

    @OneToMany(mappedBy = "route", cascade = CascadeType.ALL)
    private Set<RoutesForCarriage> routesForCarriageSet;

}