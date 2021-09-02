package com.example.delivery_system.controller;

import com.example.delivery_system.entity.Outlet;
import com.example.delivery_system.service.OutletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OutletController {
    private OutletService outletService;

    @Autowired
    public OutletController(OutletService outletService) {
        this.outletService = outletService;
    }

    @GetMapping("/outlets")
    public List<Outlet> allOutlets() {
        return outletService.allOutlets();
    }

    @GetMapping("/outlets/{id}")
    Outlet getById(@PathVariable Long id) {
        return outletService.findOutletById(id);
    }

    @PostMapping("/outlet")
    Outlet newOutlet(@RequestBody Outlet newOutlet) {
        return outletService.save(newOutlet);
    }

    @PutMapping("/outlets/{id}")
    Outlet updateOutlet(@RequestBody Outlet newOutlet, @PathVariable Long id) {
        return outletService.update(newOutlet, id);
    }

    @DeleteMapping("/outlets/{id}")
    void deleteOutlet(@PathVariable Long id) {
        outletService.deleteById(id);
    }
}
