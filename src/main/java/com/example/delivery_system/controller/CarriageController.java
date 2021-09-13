package com.example.delivery_system.controller;


import com.example.delivery_system.entity.Carriage;
import com.example.delivery_system.dto.CarriageDto;
import com.example.delivery_system.service.CarriageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/сarriages")
public class CarriageController {
    private final CarriageService carriageService;

    private final ModelMapper modelMapper;

    @GetMapping()
    public ResponseEntity<List<CarriageDto>> allCarriages() {

        List<Carriage> carriages = carriageService.allCarriages();
        return ResponseEntity.ok(carriages.stream()
            .map(this::convertToDto)
            .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    ResponseEntity <Carriage> getById(@PathVariable Long id) {
        return ResponseEntity.ok(carriageService.findCarriageById(id));
    }

    @PostMapping()
    ResponseEntity <Carriage> newCarriage(@RequestBody Carriage newCarriage) {
        return ResponseEntity.status(HttpStatus.CREATED).body(carriageService.save(newCarriage));
    }

    @PutMapping("/{id}")
    ResponseEntity <Carriage> updateCarriage(@RequestBody Carriage newCarriage, @PathVariable Long id) {
        return ResponseEntity.ok(carriageService.update(newCarriage, id));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCarriage(@PathVariable Long id) {
        carriageService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private CarriageDto convertToDto(Carriage carriage) {
        CarriageDto carriageDto = modelMapper.map(carriage, CarriageDto.class);
        return carriageDto;
    }

    private Carriage convertToEntity(CarriageDto carriageDto) throws ParseException {
        Carriage carriage = modelMapper.map(carriageDto, Carriage.class);
        return carriage;
    }
}
