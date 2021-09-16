package com.example.delivery_system.controller;

import com.example.delivery_system.dto.GoodDto;
import com.example.delivery_system.dto.OrderDto;
import com.example.delivery_system.dto.OutletDto;
import com.example.delivery_system.entity.Good;
import com.example.delivery_system.entity.GoodsInOutlets;
import com.example.delivery_system.service.DtoService;
import com.example.delivery_system.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/goods")
public class GoodController {
    private final GoodService goodService;
    private final DtoService dtoService;


    @GetMapping()
    public ResponseEntity<List<GoodDto>> allGoods() {
        List<Good> goods = goodService.allGoods();
        return ResponseEntity.ok(goods.stream()
                .map(dtoService::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    ResponseEntity<GoodDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(dtoService.convertToDto(goodService.findGoodById(id)));
    }

    @GetMapping("/orders/{id}")
    ResponseEntity<List<OrderDto>> getOrdersByGoodId(@PathVariable Long id) {
        return ResponseEntity.ok(goodService.findGoodById(id)
                .getOrderList().stream()
                .map(dtoService::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/outlets/{id}")
    ResponseEntity<List<OutletDto>> getOutletsByGoodId(@PathVariable Long id) {
        return ResponseEntity.ok(goodService.findGoodById(id)
                .getGoodsInOutlets().stream()
                .map(GoodsInOutlets::getOutlet)
                .map(dtoService::convertToDto)
                .collect(Collectors.toList()));
    }

    @PostMapping(produces = "application/json;charset=UTF-8")
    ResponseEntity<GoodDto> newGood(@RequestBody GoodDto newGood){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(dtoService.convertToDto(goodService.save(dtoService.convertToEntity(newGood))));
    }

    @PutMapping("/{id}")
    ResponseEntity<GoodDto> updateGood(@RequestBody GoodDto newGood, @PathVariable Long id){
        return ResponseEntity.ok(dtoService.convertToDto(goodService.update(dtoService.convertToEntity(newGood), id)));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteGood(@PathVariable Long id) {
        goodService.deleteById(id);
        return ResponseEntity.noContent().build();
    }


}
