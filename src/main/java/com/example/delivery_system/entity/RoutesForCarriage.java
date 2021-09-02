package com.example.delivery_system.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "carriages_routes")
public class RoutesForCarriage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int point;


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "route_id")
    private Route route;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "carriage_id")
    private Carriage carriage;


    public RoutesForCarriage(Route route, Carriage carriage, byte point) {
        this.point = point;
        this.route = route;
        this.carriage = carriage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoutesForCarriage)) return false;
        RoutesForCarriage that = (RoutesForCarriage) o;
        return Objects.equals(route.getId(), that.route.getId()) &&
                Objects.equals(carriage.getId(), that.carriage.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(route.getId(), carriage.getId(), point);
    }
}
