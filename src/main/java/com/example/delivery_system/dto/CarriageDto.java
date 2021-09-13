package com.example.delivery_system.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class CarriageDto implements Serializable {


    private Long id;

    private int capacity;

    private int point;

    private Set<RoutesForCarriageDto> routesForCarriageSet;

    private Set<OrderDto> orders;

}
