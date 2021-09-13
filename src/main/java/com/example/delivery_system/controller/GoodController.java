package com.example.delivery_system.controller;

import com.example.delivery_system.dto.GoodDto;
import com.example.delivery_system.entity.Good;
import com.example.delivery_system.service.GoodService;
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
public class GoodController {
    private final GoodService goodService;

    private final ModelMapper modelMapper;


    @GetMapping("/goods")
    public ResponseEntity<List<GoodDto>> allGoods() {
        List<Good> goods = goodService.allGoods();
        return ResponseEntity.ok(goods.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/goods/{id}")
    ResponseEntity<GoodDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(convertToDto(goodService.findGoodById(id)));
    }

    @PostMapping("/good")
    ResponseEntity<GoodDto> newGood(@RequestBody GoodDto newGood) throws ParseException {
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(goodService.save(convertToEntity(newGood))));
    }

    @PutMapping("/goods/{id}")
    ResponseEntity<GoodDto> updateGood(@RequestBody GoodDto newGood, @PathVariable Long id) throws ParseException {
        return ResponseEntity.ok(convertToDto(goodService.update(convertToEntity(newGood), id)));
    }

    @DeleteMapping("/goods/{id}")
    ResponseEntity<Void> deleteGood(@PathVariable Long id) {
        goodService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private GoodDto convertToDto(Good good) {
        GoodDto goodDto = modelMapper.map(good, GoodDto.class);
        return goodDto;
    }

    private Good convertToEntity(GoodDto goodDto) throws ParseException {
        Good good = modelMapper.map(goodDto, Good.class);
        return good;
    }
}
