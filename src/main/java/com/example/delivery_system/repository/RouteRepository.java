package com.example.delivery_system.repository;

import com.example.delivery_system.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RouteRepository extends JpaRepository<Route, Long> {
    @Query("from Route r where r.outlet_from.id = :outlet_from_id")
    List<Route> findByOutlet_from_id(long outlet_from_id);

    @Query("from Route r where r.outlet_to.id = :outlet_to_id")
    List<Route> findByOutlet_to_id(long outlet_to_id);

    @Query("from Route r where r.outlet_from.id = :outlet_from_id and r.outlet_to.id = :outlet_to_id")
    Optional<Route> findByOutlet_from_idAndOutlet_to_id(long outlet_from_id, long outlet_to_id);

}
