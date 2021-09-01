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
//    private byte point;
//
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "route_id")
//    private Route route;
//
//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "carriage_id")
//    private Carriages carriages;
//
//
//    public RoutesForCarriage(Route route, Carriages carriages, byte point) {
//        this.point = point;
//        this.route = route;
//        this.carriages = carriages;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof RoutesForCarriage)) return false;
//        RoutesForCarriage that = (RoutesForCarriage) o;
//        return Objects.equals(route.getId(), that.route.getId()) &&
//                Objects.equals(carriages.getId(), that.carriages.getId());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(route.getId(), carriages.getId(), point);
//    }
//}
