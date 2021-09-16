package com.example.delivery_system.service;

import com.example.delivery_system.dto.GoodDto;
import com.example.delivery_system.dto.GoodsInOutletsDto;
import com.example.delivery_system.dto.RouteDto;
import com.example.delivery_system.entity.Good;
import com.example.delivery_system.entity.GoodsInOutlets;
import com.example.delivery_system.entity.Outlet;
import com.example.delivery_system.entity.Route;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DbStartFillService {
    private final OutletService outletService;
    private final RouteService routeService;
    private final GoodService goodService;
    private final DtoService dtoService;

    public Outlet initOutlet(long id) {
        Outlet outlet = new Outlet();
        outlet.setName("outlet_" + id);
        outlet.setGoodsInOutlets(new ArrayList<>());
        outlet.setOrderList(new ArrayList<>());
        outlet.setRoutesFrom(new ArrayList<>());
        outlet.setRoutesTo(new ArrayList<>());
        return outlet;
    }

    public List<Route> generateRoutes(Outlet startOutlet, List<Outlet> destinationList) {
        List<Route> routeList = new ArrayList<>();
        Route newRoute;
        for (Outlet temp : destinationList) {
            int distance = generateDistance();

            newRoute = new Route();
            newRoute.setOutlet_from(startOutlet);
            newRoute.setOutlet_to(temp);
            newRoute.setRoutesForCarriage(new ArrayList<>());
            newRoute.setDistance(distance);
            routeList.add(newRoute);

            newRoute = new Route();
            newRoute.setOutlet_from(temp);
            newRoute.setOutlet_to(startOutlet);
            newRoute.setRoutesForCarriage(new ArrayList<>());
            newRoute.setDistance(distance);
            routeList.add(newRoute);
        }

        return routeList;
    }

    public List<RouteDto> fillRouteTable(){
        List<Outlet> outletList = outletService.allOutletsSortedById();
        List<RouteDto> toSaveList = new LinkedList<>();
        for (int i = 0; i < outletList.size(); i++) {
            List<Route> routesFromOutlet = routeService.allRoutesByOutletFromId(outletList.get(i).getId());
            List<Outlet> outletWithNoWayToList = new ArrayList<>(outletList);
            if (routesFromOutlet.size() > 0) {
                outletWithNoWayToList.removeAll(routesFromOutlet
                        .stream().map(Route::getOutlet_to)
                        .collect(Collectors.toList()));
            }
            outletWithNoWayToList.remove(outletList.get(i));
            List<Route> generatedRotes = generateRoutes(outletList.get(i), outletWithNoWayToList);
            RouteDto routeDto;
            for (Route temp : generatedRotes) {
                if (routeService.findRouteByOutlets(temp.getOutlet_from().getId(), temp.getOutlet_to().getId()) == null) {
                    routeDto = dtoService.convertToDto(temp);
                    routeDto.setOutlet_from_Id(temp.getOutlet_from().getId());
                    routeDto.setOutlet_to_Id(temp.getOutlet_to().getId());
                    toSaveList.add(routeDto);
//                    routeController.newRoute(routeDto);
                }
            }
        }
        return toSaveList;
    }

    public int generateDistance() {
        return new Random().nextInt(20) + 5;
    }

    public void simplifyGraph() {
        List<Outlet> outlets = outletService.allOutletsSortedById();
        int vertexCount = outlets.size();

        int[][] baseValue = new int[vertexCount][vertexCount];
        int[][] optimalPath = new int[vertexCount][vertexCount];
        int inf = 100000000;


        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                Route route = routeService.findRouteByOutlets(outlets.get(i).getId(), outlets.get(j).getId());
                if (route == null || i == j) {
                    optimalPath[i][j] = inf;
                    baseValue[i][j] = inf;
                } else {
                    optimalPath[i][j] = route.getDistance();
                    baseValue[i][j] = route.getDistance();
                }

            }
        }

        for (int k = 0; k < vertexCount; k++) {
            for (int i = 0; i < vertexCount; i++) {
                for (int j = 0; j < vertexCount; j++) {
                    optimalPath[i][j] = Math.min(optimalPath[i][j], optimalPath[i][k] + optimalPath[k][j]);
                }
            }
        }

        for (int i = 0; i < vertexCount; i++) {
            for (int j = 0; j < vertexCount; j++) {
                if (baseValue[i][j] > optimalPath[i][j] /*&&(outlets.get(i) and outlets.get(i) is not base)*/) {
                    if (optimalPath[i][j] == inf || i == j) continue;
                    Route route = routeService.findRouteByOutlets(outlets.get(i).getId(), outlets.get(j).getId());
//                    routeService.deleteById(route.getId());//simplifyGraph

                    route.setDistance(optimalPath[i][j]);
                    routeService.update(route, route.getId());
                }
            }
        }
//        for (int i = 0; i < vertexCount; i++) {
//            for (int j = 0; j < vertexCount; j++) {
//                if (g[i][j] == inf) {
////                    g[i][j] = 0;
//                    System.out.print(0 + " ");
//                } else {
//                    System.out.print(g[i][j] + " ");
//                }
//            }
//            System.out.println();
//        }
    }

    public List<GoodDto> fillOutlets(int occupancy){
        List<Outlet> outletList = outletService.allOutlets();
        List<Good> goodList = goodService.allGoods();
        int good_count = goodList.size();
        Random random = new Random();

        for (Outlet temp:outletList) {

            int goodDiversity = random.nextInt(good_count);
            Collections.shuffle(goodList);

            for (int i = 0; i <goodDiversity ; i++) {
                Good good = goodList.get(i);
                GoodsInOutlets goodsInOutlets = new GoodsInOutlets();
                goodsInOutlets.setOutlet(temp);
                goodsInOutlets.setGood(good);
                goodsInOutlets.setCount((int) (Math.round( Math.pow(random.nextInt(occupancy-1),1.5)/5) +1));
                List<GoodsInOutlets> newGoodInOutlet = good.getGoodsInOutlets();
                newGoodInOutlet.add(goodsInOutlets);
                good.setGoodsInOutlets(newGoodInOutlet);
            }
        }

        List<GoodDto> list = goodList.stream().map(dtoService::convertToDto).collect(Collectors.toList());

        return list;
    }



}
