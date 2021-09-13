package com.example.delivery_system.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

@Data
public class GoodsInOutletsDto{

    private int id;

    @JsonBackReference
    private OutletDto outlet;

    @JsonBackReference
    private GoodDto good;

    private int count;

    private long outletId;
    private long goodId;


}
