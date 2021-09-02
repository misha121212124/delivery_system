package com.example.delivery_system.controller;

import com.example.delivery_system.entity.Carriage;
import com.example.delivery_system.service.CarriageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CarriageController {
    private CarriageService carriageService;

    @Autowired
    public CarriageController(CarriageService carriageService) {
        this.carriageService = carriageService;
    }

    @GetMapping("/сarriages")
    public List<Carriage> allCarriages() {
        return carriageService.allCarriages();
    }

    @GetMapping("/сarriages/{id}")
    Carriage getById(@PathVariable Long id) {
        return carriageService.findCarriageById(id);
    }

    @PostMapping("/сarriage")
    Carriage newCarriage(@RequestBody Carriage newCarriage) {
        return carriageService.save(newCarriage);
    }

    @PutMapping("/сarriages/{id}")
    Carriage updateCarriage(@RequestBody Carriage newCarriage, @PathVariable Long id) {
        return carriageService.update(newCarriage, id);
    }

    @DeleteMapping("/сarriages/{id}")
    void deleteCarriage(@PathVariable Long id) {
        carriageService.deleteById(id);
    }
}
