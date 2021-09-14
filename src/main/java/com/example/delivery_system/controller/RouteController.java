package com.example.delivery_system.controller;

import com.example.delivery_system.dto.CarriageDto;
import com.example.delivery_system.dto.GoodDto;
import com.example.delivery_system.dto.OutletDto;
import com.example.delivery_system.dto.RouteDto;
import com.example.delivery_system.entity.Route;
import com.example.delivery_system.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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

    private final ModelMapper modelMapper;


    @GetMapping()
    public ResponseEntity<List<RouteDto>> allRoutes() {
        List<Route> routes = routeService.allRoutes();
        return ResponseEntity.ok(routes.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    ResponseEntity<RouteDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(convertToDto(routeService.findRouteById(id)));
    }

    @GetMapping("/carriages/{id}")
    ResponseEntity<List<CarriageDto>> getCarriagesByRouteId(@PathVariable Long id) {
        return ResponseEntity.ok(routeService.findRouteById(id)
                .getRoutesForCarriageSet().stream()
                .map(routesForCarriage -> modelMapper.map(routesForCarriage.getCarriage(), CarriageDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/outlet-from/{id}")
    ResponseEntity<OutletDto> getOutletFromByRouteId(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(routeService.findRouteById(id).getOutlet_from(), OutletDto.class));
    }

    @GetMapping("/outlet-to/{id}")
    ResponseEntity<OutletDto> getOutletToByRouteId(@PathVariable Long id) {
        return ResponseEntity.ok(modelMapper.map(routeService.findRouteById(id).getOutlet_to(), OutletDto.class));
    }

    @PostMapping()
    ResponseEntity<RouteDto> newRoute(@RequestBody RouteDto newRoute){
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(routeService.save(convertToEntity(newRoute))));
    }

    @PutMapping("/{id}")
    ResponseEntity<RouteDto> updateRoute(@RequestBody RouteDto newRoute, @PathVariable Long id){
        return ResponseEntity.ok(convertToDto(routeService.update(convertToEntity(newRoute), id)));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        routeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private RouteDto convertToDto(Route route) {
        RouteDto routeDto = modelMapper.map(route, RouteDto.class);
        return routeDto;
    }

    private Route convertToEntity(RouteDto routeDto){
        Route route = modelMapper.map(routeDto, Route.class);
        return route;
    }
}
