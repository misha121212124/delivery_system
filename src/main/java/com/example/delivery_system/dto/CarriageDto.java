package com.example.delivery_system.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CarriageDto implements Serializable {


    private Long id;

    private int capacity;

    private int point;

    @JsonManagedReference
    private List<RoutesForCarriageDto> routesForCarriage;

    @JsonManagedReference
    private List<OrderDto> orders;

}
