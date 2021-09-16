package com.example.delivery_system.controller;

import com.example.delivery_system.dto.*;
import com.example.delivery_system.entity.GoodsInOutlets;
import com.example.delivery_system.entity.Outlet;
import com.example.delivery_system.service.DtoService;
import com.example.delivery_system.service.OutletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/outlets")
public class OutletController {
    private final OutletService outletService;
    private final DtoService dtoService;


    @GetMapping()
    public ResponseEntity<List<OutletDto>> allOutlets() {
        List<Outlet> outlets = outletService.allOutlets();
        return ResponseEntity.ok(outlets.stream()
                .map(dtoService::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    ResponseEntity<OutletDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(dtoService.convertToDto(outletService.findOutletById(id)));
    }

    @GetMapping("/goods/{id}")
    ResponseEntity<List<GoodDto>> getGoodsByOutletId(@PathVariable Long id) {
        return ResponseEntity.ok(outletService.findOutletById(id)
                .getGoodsInOutlets().stream()
                .map(GoodsInOutlets::getGood)
                .map(dtoService::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/orders/{id}")
    ResponseEntity<List<OrderDto>> getOrdersByOutletId(@PathVariable Long id) {
        return ResponseEntity.ok(outletService.findOutletById(id)
                .getOrderList().stream()
                .map(dtoService::convertToDto)
                .collect(Collectors.toList()));
    }


    @GetMapping("/routes-from/{id}")
    ResponseEntity<List<RouteDto>> getRoutesByOutletFromId(@PathVariable Long id) {
        return ResponseEntity.ok(outletService.findOutletById(id)
                .getRoutesFrom().stream()
                .map(dtoService::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/routes-to/{id}")
    ResponseEntity<List<RouteDto>> getRoutesByOutletToId(@PathVariable Long id) {

        return ResponseEntity.ok(outletService.findOutletById(id)
                .getRoutesTo().stream()
                .map(dtoService::convertToDto)
                .collect(Collectors.toList()));
    }

    @PostMapping()
    ResponseEntity<OutletDto> newOutlet(@RequestBody OutletDto newOutlet)  {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dtoService.convertToDto(outletService.save(dtoService.convertToEntity(newOutlet))));
    }

    @PutMapping("/{id}")
    ResponseEntity<OutletDto> updateOutlet(@RequestBody OutletDto newOutlet, @PathVariable Long id)  {
        return ResponseEntity
                .ok(dtoService.convertToDto(outletService.update(dtoService.convertToEntity(newOutlet), id)));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteOutlet(@PathVariable Long id) {
        outletService.deleteById(id);
        return ResponseEntity.noContent().build();

    }


}