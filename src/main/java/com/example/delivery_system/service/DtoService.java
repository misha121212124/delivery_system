package com.example.delivery_system.service;

import com.example.delivery_system.dto.*;
import com.example.delivery_system.entity.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DtoService {
    private final OutletService outletService;
    private final GoodService goodService;

    private final ModelMapper modelMapper;

    public CarriageDto convertToDto(Carriage carriage) {
        return modelMapper.map(carriage, CarriageDto.class);
    }

    public Carriage convertToEntity(CarriageDto carriageDto) {
        return modelMapper.map(carriageDto, Carriage.class);
    }

    public OrderDto convertToDto(Order order) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        if (modelMapper.getTypeMap(Order.class, OrderDto.class) == null) {
            PropertyMap<Order, OrderDto> propertyMap = new PropertyMap<>() {
                @Override
                protected void configure() {
                    skip(destination.getCarriages());
                }
            };
            modelMapper.addMappings(propertyMap);
        }
        OrderDto orderDto = modelMapper.getTypeMap(Order.class, OrderDto.class).map(order);

        orderDto.setCarriages(order.getCarriages().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));

        return modelMapper.map(order, OrderDto.class);
    }

    public Order convertToEntity(OrderDto orderDto) {
        return modelMapper.map(orderDto, Order.class);
    }

    public RouteDto convertToDto(Route route) {
        if (modelMapper.getTypeMap(Route.class, RouteDto.class) == null) {
            PropertyMap<Route, RouteDto> propertyMap = new PropertyMap<>() {
                @Override
                protected void configure() {
                    skip(destination.getOutlet_from());
                    skip(destination.getOutlet_to());
                }
            };
            modelMapper.addMappings(propertyMap);
        }

        RouteDto routeDto = modelMapper.getTypeMap(Route.class, RouteDto.class).map(route);
//        RouteDto routeDto = modelMapper.map(route, RouteDto.class);
        routeDto.setOutlet_from_Id(route.getOutlet_from().getId());
        routeDto.setOutlet_to_Id(route.getOutlet_to().getId());
        return routeDto;
    }

    public Route convertToEntity(RouteDto routeDto) {
        routeDto.setOutlet_to(null);
        routeDto.setOutlet_from(null);
        Route route = modelMapper.map(routeDto, Route.class);
        route.setOutlet_from(outletService.findOutletById(routeDto.getOutlet_from_Id()));
        route.setOutlet_to(outletService.findOutletById(routeDto.getOutlet_to_Id()));
        return route;
    }

    public GoodDto convertToDto(Good good) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        if (modelMapper.getTypeMap(Good.class, GoodDto.class) == null) {
            PropertyMap<Good, GoodDto> propertyMap = new PropertyMap<>() {
                @Override
                protected void configure() {
//                    skip(destination.getOrderList());
                    skip(destination.getGoodsInOutlets());
                }
            };
            modelMapper.addMappings(propertyMap);
        }
        GoodDto goodDto = modelMapper.getTypeMap(Good.class, GoodDto.class).map(good);
        goodDto.setOrderList(good.getOrderList().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));

//        goodDto.setGoodsInOutlets(good.getGoodsInOutlets().stream()
//                .map(goodsInOutlets -> {
//                    long outletId = goodsInOutlets.getOutlet().getId();
//                    long goodId = goodsInOutlets.getGood().getId();
//                    goodsInOutlets.setGood(null);
//                    goodsInOutlets.setOutlet(null);
//                    GoodsInOutletsDto goodsInOutletsDto = modelMapper.map(goodsInOutlets, GoodsInOutletsDto.class);
//                    goodsInOutletsDto.setOutletId(outletId);
//                    goodsInOutletsDto.setGoodId(goodId);
//                    return goodsInOutletsDto;
//                }).collect(Collectors.toList()));

        goodDto.setGoodsInOutlets(good.getGoodsInOutlets().stream()
                .map(this::convertToDto).collect(Collectors.toList()));

//                .map(goodsInOutletsDto -> {
//                    goodsInOutletsDto.setOutletId(goodsInOutletsDto.getOutlet().getId());
//                    goodsInOutletsDto.setGoodId(goodsInOutletsDto.getGood().getId());
//                    goodsInOutletsDto.setGood(null);
//                    goodsInOutletsDto.setOutlet(null);
//                    return goodsInOutletsDto;
//                }).collect(Collectors.toList()));


//        for (GoodsInOutletsDto temp : goodDto.getGoodsInOutlets()) {
//            temp.setOutletId(temp.getOutlet().getId());
//            temp.setGoodId(temp.getGood().getId());
//        }
        return goodDto;
    }

    public Good convertToEntity(GoodDto goodDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        if (modelMapper.getTypeMap(GoodDto.class, Good.class) == null) {
            PropertyMap<GoodDto, Good> propertyMap = new PropertyMap<>() {
                @Override
                protected void configure() {
                    skip(destination.getGoodsInOutlets());
                }
            };
            modelMapper.addMappings(propertyMap);
        }
        Good good = modelMapper.getTypeMap(GoodDto.class, Good.class).map(goodDto);
        good.setOrderList(goodDto.getOrderList().stream()
                .map(this::convertToEntity)
                .collect(Collectors.toList()));


        good.setGoodsInOutlets(goodDto.getGoodsInOutlets().stream()
                .map(this::convertToEntity).collect(Collectors.toList()));

        return good;
    }

    //finished
    public OutletDto convertToDto(Outlet outlet) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        if (modelMapper.getTypeMap(Outlet.class, OutletDto.class) == null) {
            PropertyMap<Outlet, OutletDto> propertyMap = new PropertyMap<>() {
                @Override
                protected void configure() {
                    skip(destination.getRoutesTo());
                    skip(destination.getRoutesFrom());
                    skip(destination.getGoodsInOutlets());
                }
            };
            modelMapper.addMappings(propertyMap);
        }

        OutletDto outletDto = modelMapper.getTypeMap(Outlet.class, OutletDto.class).map(outlet);

        outletDto.setRoutesTo(outlet.getRoutesTo().stream()
                .map(this::convertToDto).collect(Collectors.toList()));

        outletDto.setRoutesFrom(outlet.getRoutesFrom().stream()
                .map(this::convertToDto).collect(Collectors.toList()));

        outletDto.setGoodsInOutlets(outlet.getGoodsInOutlets().stream()
                .map(this::convertToDto).collect(Collectors.toList()));

        return outletDto;
    }

    public Outlet convertToEntity(OutletDto outletDto) {
        return modelMapper.map(outletDto, Outlet.class);
    }

    public GoodsInOutletsDto convertToDto(GoodsInOutlets goodsInOutlets) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        if (modelMapper.getTypeMap(GoodsInOutlets.class, GoodsInOutletsDto.class) == null) {
            PropertyMap<GoodsInOutlets, GoodsInOutletsDto> propertyMap = new PropertyMap<>() {
                @Override
                protected void configure() {
                    skip(destination.getGood());
                    skip(destination.getOutlet());
                }
            };
            modelMapper.addMappings(propertyMap);
        }

        GoodsInOutletsDto goodsInOutletsDto = modelMapper
                .getTypeMap(GoodsInOutlets.class,GoodsInOutletsDto.class)
                .map(goodsInOutlets);

        goodsInOutletsDto.setOutletId(goodsInOutlets.getOutlet().getId());
        goodsInOutletsDto.setGoodId(goodsInOutlets.getGood().getId());

        return goodsInOutletsDto;
    }

    public GoodsInOutlets convertToEntity(GoodsInOutletsDto goodsInOutletsDto) {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        if (modelMapper.getTypeMap(GoodsInOutletsDto.class, GoodsInOutlets.class) == null) {
            PropertyMap<GoodsInOutletsDto, GoodsInOutlets> propertyMap = new PropertyMap<>() {
                @Override
                protected void configure() {
                    skip(destination.getGood());
                    skip(destination.getOutlet());
                }
            };
            modelMapper.addMappings(propertyMap);
        }
        GoodsInOutlets goodsInOutlets =  modelMapper
                .getTypeMap(GoodsInOutletsDto.class,GoodsInOutlets.class)
                .map(goodsInOutletsDto);
        goodsInOutlets.setOutlet(outletService.findOutletById(goodsInOutletsDto.getOutletId()));
        goodsInOutlets.setGood(goodService.findGoodById(goodsInOutletsDto.getGoodId()));

        return goodsInOutlets;
    }

}
