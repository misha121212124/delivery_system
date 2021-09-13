package com.example.delivery_system.service;

import com.example.delivery_system.entity.Outlet;
import com.example.delivery_system.entity.Route;
import com.example.delivery_system.repository.OutletRepository;
import com.example.delivery_system.repository.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class RouteServiceTest {
    @Mock
    RouteRepository routeRepository;
    @Mock
    OutletRepository outletRepository;

    RouteService routeService;

    @BeforeEach
    void setUp() {
        routeService = new RouteService(routeRepository,outletRepository);
    }

    @Test
    void findRouteById() {
        when(routeRepository.findById(1L)).thenReturn(Optional.of(new Route()));
        Route route = routeService.findRouteById(1L);
        assertNotNull(route);
    }

    @Test
    void update() {
        Route updateRoute = new Route();
        updateRoute.setId(8L);
        updateRoute.setDistance(40);
        when(routeRepository.save(any(Route.class))).thenReturn(updateRoute);
        when(routeRepository.findById(8L)).thenReturn(Optional.of(new Route())
                .map(route -> {
                    route.setId(8L);
                    return route;
                }));
        updateRoute = routeService.update(updateRoute, 8L);
        assertNotNull(updateRoute);
    }

    @Test
    void allRoutes() {
        List<Route> list = new ArrayList<>();
        list.add(new Route());
        when(routeRepository.findAll(any(org.springframework.data.domain.Sort.class))).thenReturn(list);
        List routes = routeService.allRoutes();
        assertNotNull(routes);
    }

    @Test
    void save() {
        Route saveRoute = new Route();
        saveRoute.setId(10L);
        saveRoute.setDistance(60);
        when(routeRepository.save(any(Route.class))).thenReturn(saveRoute);
        saveRoute = routeService.save(saveRoute);
        assertNotNull(saveRoute);
    }

    @Test
    void deleteById() {// what should I do with it?
        routeService.deleteById(2L);
        assert true;
    }
}