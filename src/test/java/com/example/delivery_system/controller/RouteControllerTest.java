package com.example.delivery_system.controller;

import com.example.delivery_system.dto.OutletDto;
import com.example.delivery_system.dto.RouteDto;
import com.example.delivery_system.entity.Outlet;
import com.example.delivery_system.entity.Route;
import com.example.delivery_system.repository.OutletRepository;
import com.example.delivery_system.repository.RouteRepository;
import com.example.delivery_system.service.RouteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteControllerTest {
    @Mock
    RouteRepository routeRepository;
    @Mock
    OutletRepository outletRepository;

    RouteController routeController;

    @BeforeEach
    void setUp() {
        routeController = new RouteController(new RouteService(routeRepository,outletRepository), new ModelMapper());

    }

    @Test
    void allRoutes() {
        List<Route> list = new ArrayList<>();
        Route testRoute = new Route();
        testRoute.setRoutesForCarriageSet(new HashSet<>());
        testRoute.setOutlet_to(new Outlet());
        testRoute.setOutlet_from(new Outlet());
        list.add(testRoute);
        when(routeRepository.findAll(any(org.springframework.data.domain.Sort.class))).thenReturn(list);
        ResponseEntity<List<RouteDto>> listDto = routeController.allRoutes();
        System.out.println(listDto);
        assertNotNull(listDto);
    }

    @Test
    void getById() {
        Route testRoute = new Route();
        testRoute.setRoutesForCarriageSet(new HashSet<>());
        testRoute.setOutlet_to(new Outlet());
        testRoute.setOutlet_from(new Outlet());
        when(routeRepository.findById(1L)).thenReturn(Optional.of(testRoute));
        ResponseEntity<RouteDto> route = routeController.getById(1L);
        System.out.println(route);
        assertNotNull(route);
    }

    @Test
    void newRoute() throws ParseException {
        RouteDto routeDto = new RouteDto();
        routeDto.setRoutesForCarriageSet(new HashSet<>());
        routeDto.setOutlet_to(new OutletDto());
        routeDto.setOutlet_from(new OutletDto());
        Route forSave = new Route();
        forSave.setRoutesForCarriageSet(new HashSet<>());
        forSave.setOutlet_to(new Outlet());
        forSave.setOutlet_from(new Outlet());
        when(routeRepository.save(any(Route.class))).thenReturn(forSave);
        ResponseEntity<RouteDto> savedRoute = routeController.newRoute(routeDto);
        System.out.println(savedRoute);
        assertNotNull(savedRoute);
    }

    @Test
    void updateRoute() throws ParseException {
        RouteDto routeDto = new RouteDto();
        routeDto.setRoutesForCarriageSet(new HashSet<>());
        routeDto.setOutlet_to(new OutletDto());
        routeDto.setOutlet_from(new OutletDto());
        routeDto.setId(6L);
        Route forSave = new Route();
        forSave.setOutlet_to(new Outlet());
        forSave.setOutlet_from(new Outlet());        when(routeRepository.save(any(Route.class))).thenReturn(forSave);
        when(routeRepository.findById(6L)).thenReturn(Optional.of(new Route())
                .map(route -> {
                    route.setId(6L);
                    return route;
                }));
        ResponseEntity<RouteDto> savedRoute = routeController.updateRoute(routeDto,6L);
        System.out.println(savedRoute);
        assertNotNull(savedRoute);
    }

    @Test
    void deleteRoute() {
        ResponseEntity<Void> delResult = routeController.deleteRoute(1L);
        System.out.println(delResult);
        assertNotNull(delResult);
    }
}