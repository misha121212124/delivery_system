package com.example.delivery_system.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "outlets_goods")
public class GoodsInOutlets implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "outlet_id")
    private Outlet outlet;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "good_id")
    private Good good;

    private int count;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GoodsInOutlets)) return false;
        GoodsInOutlets that = (GoodsInOutlets) o;
        return Objects.equals(outlet.getId(), that.outlet.getId()) &&
                Objects.equals(good.getId(), that.good.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(outlet.getName(), good.getName(), count);
    }

    @Override
    public String toString() {
        return "GoodsInOutlets{" +
                "id=" + id +
                ", outlet_id=" + outlet.getId() +
                ", good_id=" + good.getId() +
                ", count=" + count +
                '}';
    }
}
