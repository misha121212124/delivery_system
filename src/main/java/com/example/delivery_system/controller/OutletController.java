package com.example.delivery_system.controller;

import com.example.delivery_system.entity.Outlet;
import com.example.delivery_system.service.OutletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OutletController {
    private OutletService outletService;

    @Autowired
    public OutletController(OutletService outletService) {
        this.outletService = outletService;
    }

    @GetMapping("/outlet/all")
    public List<Outlet> allOutlets() {
        return outletService.allOutlets();
    }
}
