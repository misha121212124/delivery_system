package com.example.delivery_system.dto;

import lombok.Data;
import java.io.Serializable;

@Data
public class RoutesForCarriageDto implements Serializable {

    private int id;

    private int point;

    private RouteDto route;

    private CarriageDto carriage;

}
