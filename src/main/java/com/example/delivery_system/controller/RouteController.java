package com.example.delivery_system.controller;

import com.example.delivery_system.entity.Route;
import com.example.delivery_system.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RouteController {
    private RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping("/routes")
    public List<Route> allRoutes() {
        return routeService.allRoutes();
    }

    @GetMapping("/routes/{id}")
    Route getById(@PathVariable Long id) {
        return routeService.findRouteById(id);
    }

    @PostMapping("/route")
    Route newRoute(@RequestBody Route newRoute) {
        return routeService.save(newRoute);
    }

    @PutMapping("/routes/{id}")
    Route updateRoute(@RequestBody Route newRoute, @PathVariable Long id) {
        return routeService.update(newRoute, id);
    }

    @DeleteMapping("/routes/{id}")
    void deleteRoute(@PathVariable Long id) {
        routeService.deleteById(id);
    }
}
