package com.example.delivery_system.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class RouteDto implements Serializable {

    private Long id;

    private float distance;

    private OutletDto outlet_from;

    private OutletDto outlet_to;

    private Set<RoutesForCarriageDto> routesForCarriageSet;

}