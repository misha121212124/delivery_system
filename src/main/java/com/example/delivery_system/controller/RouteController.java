package com.example.delivery_system.controller;

import com.example.delivery_system.dto.RouteDto;
import com.example.delivery_system.entity.Route;
import com.example.delivery_system.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class RouteController {
    private final RouteService routeService;

    private final ModelMapper modelMapper;


    @GetMapping("/routes")
    public ResponseEntity<List<RouteDto>> allRoutes() {
        List<Route> routes = routeService.allRoutes();
        return ResponseEntity.ok(routes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/routes/{id}")
    ResponseEntity<RouteDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(convertToDto(routeService.findRouteById(id)));
    }

    @PostMapping("/route")
    ResponseEntity<RouteDto> newRoute(@RequestBody RouteDto newRoute) throws ParseException {
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(routeService.save(convertToEntity(newRoute))));
    }

    @PutMapping("/routes/{id}")
    ResponseEntity<RouteDto> updateRoute(@RequestBody RouteDto newRoute, @PathVariable Long id) throws ParseException {
        return ResponseEntity.ok(convertToDto(routeService.update(convertToEntity(newRoute), id)));
    }

    @DeleteMapping("/routes/{id}")
    ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private RouteDto convertToDto(Route route) {
        RouteDto routeDto = modelMapper.map(route, RouteDto.class);
        return routeDto;
    }

    private Route convertToEntity(RouteDto routeDto) throws ParseException {
        Route route = modelMapper.map(routeDto, Route.class);
        return route;
    }
}
