package com.example.delivery_system.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;
import java.io.Serializable;

@Data
public class RoutesForCarriageDto implements Serializable {

    private int id;

    private int point;

    @JsonBackReference
    private RouteDto route;

    @JsonBackReference
    private CarriageDto carriage;

}
