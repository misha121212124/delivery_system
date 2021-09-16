package com.example.delivery_system.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OutletDto implements Serializable {

    private Long id;

    private String name;

    @JsonManagedReference
    private List<GoodsInOutletsDto> goodsInOutlets;

    @JsonManagedReference
    private List<RouteDto> routesFrom;

    @JsonManagedReference
    private List<RouteDto> routesTo;

    @JsonManagedReference
    private List<OrderDto> orderList;

}