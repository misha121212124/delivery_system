package com.example.delivery_system.service;

import com.example.delivery_system.entity.Carriage;
import com.example.delivery_system.repository.CarriageRepository;
import com.example.delivery_system.repository.OrderRepository;
import com.example.delivery_system.repository.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarriageService {

    private final CarriageRepository carriageRepository;
    private final RouteRepository routeRepository;
    private final OrderRepository orderRepository;

    public Carriage findCarriageById(Long carriageId) {
        Optional<Carriage> carriageFromDb = carriageRepository.findById(carriageId);
        return carriageFromDb.orElse(new Carriage());
    }

    public Carriage update(Carriage newCarriage, Long oldId) {
        return carriageRepository.findById(oldId).
                map(carriage -> {
                    carriage.setCapacity(newCarriage.getCapacity());
//                    carriage.setOrdersOnCarriageSet(newCarriage.getOrdersOnCarriageSet());
                    carriage.setOrders(newCarriage.getOrders());
                    carriage.setRoutesForCarriage(newCarriage.getRoutesForCarriage());
                    carriage.setPoint(newCarriage.getPoint());
                    return carriageRepository.save(carriage);
                })
                .orElseGet(() -> {
                    newCarriage.setId(oldId);
                    return carriageRepository.save(newCarriage);
                });
    }

    public List<Carriage> allCarriages() {
        return carriageRepository.findAll(Sort.by("capacity"));
    }


    public Carriage save(Carriage newCarriage){
        return carriageRepository.save(newCarriage);
    }

    public void deleteById(long carriageId){
        carriageRepository.deleteById(carriageId);
    }
}
