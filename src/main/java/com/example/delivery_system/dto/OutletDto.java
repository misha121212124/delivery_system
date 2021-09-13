package com.example.delivery_system.dto;

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
}

