package com.example.delivery_system.dto;

import com.example.delivery_system.entity.Order;
import com.example.delivery_system.entity.Route;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Data
public class OutletDto implements Serializable {

    private Long id;

    private String name;

    @JsonManagedReference
    private List<GoodsInOutletsDto> goodsInOutletsSet;

    @JsonManagedReference
    private List<Route> routesFrom;

    @JsonManagedReference
    private List<Route> routesTo;

    @JsonManagedReference
    private List<Order> orderList;

}