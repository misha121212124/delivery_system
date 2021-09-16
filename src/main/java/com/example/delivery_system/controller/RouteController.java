package com.example.delivery_system.controller;

import com.example.delivery_system.dto.CarriageDto;
import com.example.delivery_system.dto.OutletDto;
import com.example.delivery_system.dto.RouteDto;
import com.example.delivery_system.entity.Route;
import com.example.delivery_system.entity.RoutesForCarriage;
import com.example.delivery_system.service.DtoService;
import com.example.delivery_system.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/routes")
public class RouteController {
    private final RouteService routeService;
    private final DtoService dtoService;

    @GetMapping()
    public ResponseEntity<List<RouteDto>> allRoutes() {
        List<Route> routes = routeService.allRoutes();
        return ResponseEntity.ok(routes.stream()
                .map(dtoService::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    ResponseEntity<RouteDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(dtoService.convertToDto(routeService.findRouteById(id)));
    }

    @GetMapping("/carriages/{id}")
    ResponseEntity<List<CarriageDto>> getCarriagesByRouteId(@PathVariable Long id) {
        return ResponseEntity.ok(routeService.findRouteById(id)
                .getRoutesForCarriage().stream()
                .map(RoutesForCarriage::getCarriage)
                .map(dtoService::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/outlet-from/{id}")
    ResponseEntity<OutletDto> getOutletFromByRouteId(@PathVariable Long id) {
        return ResponseEntity.ok(dtoService.convertToDto(routeService.findRouteById(id).getOutlet_from()));
    }

    @GetMapping("/outlet-to/{id}")
    ResponseEntity<OutletDto> getOutletToByRouteId(@PathVariable Long id) {
        return ResponseEntity.ok(dtoService.convertToDto(routeService.findRouteById(id).getOutlet_to()));
    }

    @PostMapping()
    ResponseEntity<RouteDto> newRoute(@RequestBody RouteDto newRoute){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dtoService.convertToDto(routeService.save(dtoService.convertToEntity(newRoute))));
    }

    @PutMapping("/{id}")
    ResponseEntity<RouteDto> updateRoute(@RequestBody RouteDto newRoute, @PathVariable Long id){
        return ResponseEntity.ok(dtoService.convertToDto(routeService.update(dtoService.convertToEntity(newRoute), id)));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
