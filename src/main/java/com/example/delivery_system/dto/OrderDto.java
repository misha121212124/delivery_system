package com.example.delivery_system.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class OrderDto implements Serializable {

    private Long id;

    private int count;

    @JsonBackReference
    private OutletDto outlet;

    @JsonBackReference
    private GoodDto good;

    @JsonManagedReference
    private List<CarriageDto> carriages;

}
