package com.example.delivery_system.controller;

import com.example.delivery_system.dto.OrderDto;
import com.example.delivery_system.dto.RouteDto;
import com.example.delivery_system.entity.Carriage;
import com.example.delivery_system.dto.CarriageDto;
import com.example.delivery_system.entity.RoutesForCarriage;
import com.example.delivery_system.service.CarriageService;
import com.example.delivery_system.service.DtoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/carriages")
public class CarriageController {
    private final CarriageService carriageService;
    private final DtoService dtoService;

    @GetMapping()
    public ResponseEntity<List<CarriageDto>> allCarriages() {

        List<Carriage> carriages = carriageService.allCarriages();
        return ResponseEntity.ok(carriages.stream()
                .map(dtoService::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    ResponseEntity<CarriageDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(dtoService.convertToDto(carriageService.findCarriageById(id)));
    }

    @GetMapping("/routes/{id}")
    ResponseEntity<List<RouteDto>> getRoutesByCarriageId(@PathVariable Long id) {
        return ResponseEntity.ok(carriageService.findCarriageById(id)
                .getRoutesForCarriage().stream()
                .map(RoutesForCarriage::getRoute)
                .map(dtoService::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/orders/{id}")
    ResponseEntity<List<OrderDto>> getOrdersByCarriageId(@PathVariable Long id) {
        return ResponseEntity.ok(carriageService.findCarriageById(id)
                .getOrders().stream()
                .map(dtoService::convertToDto)
                .collect(Collectors.toList()));
    }

    @PostMapping()
    ResponseEntity <CarriageDto> newCarriage(@RequestBody CarriageDto newCarriage) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dtoService.convertToDto(carriageService.save(dtoService.convertToEntity(newCarriage))));
    }

    @PutMapping("/{id}")
    ResponseEntity <CarriageDto> updateCarriage(@RequestBody CarriageDto newCarriage, @PathVariable Long id) {
        return ResponseEntity
                .ok(dtoService.convertToDto(carriageService.update(dtoService.convertToEntity(newCarriage), id)));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCarriage(@PathVariable Long id) {
        carriageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}