package com.example.delivery_system.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class GoodDto implements Serializable {

    private Long id;

    private String name;

    private int volume;

    @JsonManagedReference
    private List<GoodsInOutletsDto> goodsInOutlets;

    @JsonManagedReference
    private List<OrderDto> orderList;
    //private List<Order> orderList; // this make exeption



}
