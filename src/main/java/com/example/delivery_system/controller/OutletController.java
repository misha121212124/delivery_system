package com.example.delivery_system.controller;

import com.example.delivery_system.dto.*;
import com.example.delivery_system.entity.Outlet;
import com.example.delivery_system.service.OutletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/outlets")
public class OutletController {
    private final OutletService outletService;

    private final ModelMapper modelMapper;


    @GetMapping()
    public ResponseEntity<List<OutletDto>> allOutlets() {
        List<Outlet> outlets = outletService.allOutlets();
        return ResponseEntity.ok(outlets.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    ResponseEntity<OutletDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(convertToDto(outletService.findOutletById(id)));
    }



    @GetMapping("/goods/{id}")
    ResponseEntity<List<GoodDto>> getGoodsByOutletId(@PathVariable Long id) {
        return ResponseEntity.ok(outletService.findOutletById(id)
                .getGoodsInOutletsSet().stream()
                .map(goodInOutlets -> modelMapper.map(goodInOutlets.getGood(), GoodDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/orders/{id}")
    ResponseEntity<List<OrderDto>> getOrdersByOutletId(@PathVariable Long id) {
        return ResponseEntity.ok(outletService.findOutletById(id)
                .getOrderList().stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList()));
    }


    @GetMapping("/routes-from/{id}")
    ResponseEntity<List<RouteDto>> getRoutesByOutletFromId(@PathVariable Long id) {
        return ResponseEntity.ok(outletService.findOutletById(id)
                .getRoutesFrom().stream()
                .map(route -> modelMapper.map(route, RouteDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/routes-to/{id}")
    ResponseEntity<List<RouteDto>> getRoutesByOutletToId(@PathVariable Long id) {

        return ResponseEntity.ok(outletService.findOutletById(id)
                .getRoutesTo().stream()
                .map(route -> modelMapper.map(route, RouteDto.class))
                .collect(Collectors.toList()));
    }

    @PostMapping()
    ResponseEntity<OutletDto> newOutlet(@RequestBody OutletDto newOutlet) throws ParseException {
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(outletService.save(convertToEntity(newOutlet))));
    }

    @PutMapping("/{id}")
    ResponseEntity<OutletDto> updateOutlet(@RequestBody OutletDto newOutlet, @PathVariable Long id) throws ParseException {
        return ResponseEntity.ok(convertToDto(outletService.update(convertToEntity(newOutlet), id)));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteOutlet(@PathVariable Long id) {
        outletService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    private OutletDto convertToDto(Outlet outlet) {
        OutletDto outletDto = modelMapper.map(outlet, OutletDto.class);
        for (GoodsInOutletsDto temp : outletDto.getGoodsInOutletsSet()) {
            temp.setOutletId(temp.getOutlet().getId());
            temp.setGoodId(temp.getGood().getId());
        }
        return outletDto;
    }

    private Outlet convertToEntity(OutletDto outletDto) throws ParseException {
        Outlet outlet = modelMapper.map(outletDto, Outlet.class);
        return outlet;
    }
}
