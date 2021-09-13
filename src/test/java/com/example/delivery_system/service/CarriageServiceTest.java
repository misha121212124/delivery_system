package com.example.delivery_system.service;

import com.example.delivery_system.entity.Carriage;
import com.example.delivery_system.repository.CarriageRepository;
import com.example.delivery_system.repository.OrderRepository;
import com.example.delivery_system.repository.RouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarriageServiceTest {
    @Mock
    CarriageRepository carriageRepository;
    @Mock
    RouteRepository routeRepository;
    @Mock
    OrderRepository orderRepository;

    CarriageService carriageService;

    @BeforeEach
    void setUp() {
        carriageService = new CarriageService(carriageRepository,routeRepository,orderRepository);
    }

    @Test
    void findCarriageById() {
        when(carriageRepository.findById(1L)).thenReturn(Optional.of(new Carriage()));
        Carriage carriage = carriageService.findCarriageById(1L);
        assertNotNull(carriage);
    }

    @Test
    void update() {
        Carriage updateCurriage = new Carriage();
        updateCurriage.setId(8L);
        updateCurriage.setCapacity(80);
        when(carriageRepository.save(any(Carriage.class))).thenReturn(updateCurriage);
        when(carriageRepository.findById(8L)).thenReturn(Optional.of(new Carriage())
                .map(carriage -> {
                    carriage.setId(8L);
                    return carriage;
                }));
        updateCurriage = carriageService.update(updateCurriage, 8L);
        assertNotNull(updateCurriage);
    }

    @Test
    void allCarriages() {
        List<Carriage> list = new ArrayList<>();
        list.add(new Carriage());
        when(carriageRepository.findAll(any(org.springframework.data.domain.Sort.class))).thenReturn(list);
        List carriages = carriageService.allCarriages();
        assertNotNull(carriages);
    }

    @Test
    void save() {
        Carriage saveCarriage = new Carriage();
        saveCarriage.setId(10L);
        saveCarriage.setCapacity(60);
        when(carriageRepository.save(any(Carriage.class))).thenReturn(saveCarriage);
        saveCarriage = carriageService.save(saveCarriage);
        assertNotNull(saveCarriage);
    }

    @Test
    void deleteById() {// what should I do with it?
        carriageService.deleteById(2L);
        assert true;
    }
}