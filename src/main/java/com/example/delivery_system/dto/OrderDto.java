package com.example.delivery_system.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class OrderDto implements Serializable {

    private Long id;

    private int count;

    private OutletDto outlet;

    private GoodDto good;

    private Set<CarriageDto> carriages;

}
