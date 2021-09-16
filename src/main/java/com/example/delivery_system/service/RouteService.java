package com.example.delivery_system.service;

import com.example.delivery_system.entity.Route;
import com.example.delivery_system.repository.OutletRepository;
import com.example.delivery_system.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class RouteService {
    private final RouteRepository routeRepository;
    private final OutletRepository outletRepository;

    public Route findRouteById(Long routeId) {
        Optional<Route> routeFromDb = routeRepository.findById(routeId);
        return routeFromDb.orElse(new Route());
    }

    public List<Route> allRoutesByOutletFromId(Long outletFromId){
        return routeRepository.findByOutlet_from_id(outletFromId);
    }

    public List<Route> allRoutesByOutletToId(Long outletToId){
        return routeRepository.findByOutlet_to_id(outletToId);
    }

    public Route findRouteByOutlets(Long outletFromId, Long outletToId){
        Optional<Route> route = routeRepository.findByOutlet_from_idAndOutlet_to_id(outletFromId, outletToId);
        return route.orElse(null);
    }

    public Route update(Route newRoute, Long oldId) {
        return routeRepository.findById(oldId).
                map(route -> {
                    route.setOutlet_from(newRoute.getOutlet_from());
                    route.setOutlet_to(newRoute.getOutlet_to());
                    route.setDistance(newRoute.getDistance());
                    route.setRoutesForCarriage(newRoute.getRoutesForCarriage());
                    return routeRepository.save(route);
                })
                .orElseGet(() -> {
                    newRoute.setId(oldId);
                    return routeRepository.save(newRoute);
                });
    }

    public List<Route> allRoutes() {
        return routeRepository.findAll(Sort.by("distance"));
    }

    public Route save(Route newRoute){
        return routeRepository.save(newRoute);
    }

    public void deleteById(long routeId){
        routeRepository.deleteById(routeId);
    }
}
