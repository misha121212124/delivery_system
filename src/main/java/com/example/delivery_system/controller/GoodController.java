package com.example.delivery_system.controller;

import com.example.delivery_system.dto.GoodDto;
import com.example.delivery_system.dto.OrderDto;
import com.example.delivery_system.dto.OutletDto;
import com.example.delivery_system.entity.Good;
import com.example.delivery_system.service.GoodService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/goods")
public class GoodController {
    private final GoodService goodService;

    private final ModelMapper modelMapper;


    @GetMapping()
    public ResponseEntity<List<GoodDto>> allGoods() {
        List<Good> goods = goodService.allGoods();
        return ResponseEntity.ok(goods.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    ResponseEntity<GoodDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(convertToDto(goodService.findGoodById(id)));
    }

    @GetMapping("/orders/{id}")
    ResponseEntity<List<OrderDto>> getOrdersByGoodId(@PathVariable Long id) {
        return ResponseEntity.ok(goodService.findGoodById(id)
                .getOrderList().stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/outlets/{id}")
    ResponseEntity<List<OutletDto>> getOutletsByGoodId(@PathVariable Long id) {
        return ResponseEntity.ok(goodService.findGoodById(id)
                .getGoodsInOutlets().stream()
                .map(goodsInOutlets -> modelMapper.map(goodsInOutlets.getOutlet(), OutletDto.class))
                .collect(Collectors.toList()));
    }

    @PostMapping()
    ResponseEntity<GoodDto> newGood(@RequestBody GoodDto newGood){
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(goodService.save(convertToEntity(newGood))));
    }

    @PutMapping("/{id}")
    ResponseEntity<GoodDto> updateGood(@RequestBody GoodDto newGood, @PathVariable Long id){
        return ResponseEntity.ok(convertToDto(goodService.update(convertToEntity(newGood), id)));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteGood(@PathVariable Long id) {
        goodService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private GoodDto convertToDto(Good good) {
        GoodDto goodDto = modelMapper.map(good, GoodDto.class);
        return goodDto;
    }

    private Good convertToEntity(GoodDto goodDto){
        Good good = modelMapper.map(goodDto, Good.class);
        return good;
    }
}
