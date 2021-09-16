package com.example.delivery_system.controller;

import com.example.delivery_system.dto.GoodDto;
import com.example.delivery_system.dto.GoodsInOutletsDto;
import com.example.delivery_system.dto.OutletDto;
import com.example.delivery_system.dto.RouteDto;
import com.example.delivery_system.entity.Outlet;
import com.example.delivery_system.entity.Route;
import com.example.delivery_system.service.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Controller
@RequestMapping("/test")
public class DbStartFillController {
    private final OutletService outletService;
    private final RouteService routeService;
    private final GoodService goodService;
    private final DtoService dtoService;
    private final DbStartFillService dbStartFillService;

    private final ModelMapper modelMapper;

    @GetMapping("/new/outlets/{count}")
    public ResponseEntity<List<OutletDto>> createOutlets(@PathVariable int count) {
        OutletController outletController = new OutletController(outletService, dtoService);
        int id = new Random().nextInt();
        for (int i = 0; i < count; i++) {
            outletController.newOutlet(dtoService.convertToDto(dbStartFillService.initOutlet(id + i)));
        }
        return outletController.allOutlets();
    }

    @GetMapping("/new/routes/fill")
    public ResponseEntity<List<RouteDto>> fillRoutesTable() {
        RouteController routeController = new RouteController(routeService, dtoService);
        for (RouteDto temp:dbStartFillService.fillRouteTable()) {
            routeController.newRoute(temp);
        }
        return routeController.allRoutes();
    }

    @GetMapping("/new/routes/simplify")
    public ResponseEntity<List<RouteDto>> simplifyRoutesTable() {
        RouteController routeController = new RouteController(routeService, dtoService);
        dbStartFillService.simplifyGraph();
        return routeController.allRoutes();
    }

    @GetMapping("new/goods/sow/{occupancy}")
    public ResponseEntity<List<GoodDto>> fillOutlets(@PathVariable int occupancy){
        GoodController goodController = new GoodController(goodService,dtoService);
        List<GoodDto> goodDtoList = dbStartFillService.fillOutlets(occupancy);
        for (GoodDto temp: goodDtoList) {
            goodController.newGood(temp);
        }
        return ResponseEntity.ok(goodDtoList);
    }







}
