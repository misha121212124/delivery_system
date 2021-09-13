package com.example.delivery_system.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RouteDto implements Serializable {

    private Long id;

    private float distance;

    @JsonBackReference
    private OutletDto outlet_from;

    @JsonBackReference
    private OutletDto outlet_to;

    @JsonManagedReference
    private List<RoutesForCarriageDto> routesForCarriageSet;

}