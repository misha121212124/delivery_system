package com.example.delivery_system.controller;

import com.example.delivery_system.entity.Good;
import com.example.delivery_system.service.GoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GoodController {
    private GoodService goodService;

    @Autowired
    public GoodController(GoodService goodService) {
        this.goodService = goodService;
    }

    @GetMapping("/goods")
    public List<Good> allGoods() {
        return goodService.allGoods();
    }

    @GetMapping("/goods/{id}")
    Good getById(@PathVariable Long id) {
        return goodService.findGoodById(id);
    }

    @PostMapping("/good")
    Good newGood(@RequestBody Good newGood) {
        return goodService.save(newGood);
    }

    @PutMapping("/goods/{id}")
    Good updateGood(@RequestBody Good newGood, @PathVariable Long id) {
        return goodService.update(newGood, id);
    }

    @DeleteMapping("/goods/{id}")
    void deleteGood(@PathVariable Long id) {
        goodService.deleteById(id);
    }
}
