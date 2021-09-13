package com.example.delivery_system.controller;

import com.example.delivery_system.dto.CarriageDto;
import com.example.delivery_system.entity.Carriage;
import com.example.delivery_system.repository.CarriageRepository;
import com.example.delivery_system.repository.OrderRepository;
import com.example.delivery_system.repository.CarriageRepository;
import com.example.delivery_system.repository.RouteRepository;
import com.example.delivery_system.service.CarriageService;
import com.example.delivery_system.service.CarriageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CarriageControllerTest {
    @Mock
    CarriageRepository carriageRepository;
    @Mock
    RouteRepository routeRepository;
    @Mock
    OrderRepository orderRepository;

    CarriageController carriageController;

    @BeforeEach
    void setUp() {
        carriageController = new CarriageController(new CarriageService(carriageRepository, routeRepository, orderRepository), new ModelMapper());

    }

    @Test
    void allCarriages() {
        List<Carriage> list = new ArrayList<>();
        Carriage testCarriage = new Carriage();
        testCarriage.setOrders(new HashSet<>());
        testCarriage.setRoutesForCarriageSet(new HashSet<>());
        list.add(testCarriage);
        when(carriageRepository.findAll(any(org.springframework.data.domain.Sort.class))).thenReturn(list);
        ResponseEntity<List<CarriageDto>> listDto = carriageController.allCarriages();
        System.out.println(listDto);
        assertNotNull(listDto);
    }

    @Test
    void getById() {
        Carriage testCarriage = new Carriage();
        testCarriage.setOrders(new HashSet<>());
        testCarriage.setRoutesForCarriageSet(new HashSet<>());
        when(carriageRepository.findById(1L)).thenReturn(Optional.of(testCarriage));
        ResponseEntity<CarriageDto> carriage = carriageController.getById(1L);
        System.out.println(carriage);
        assertNotNull(carriage);
    }

    @Test
    void newCarriage() throws ParseException {
        CarriageDto carriageDto = new CarriageDto();
        carriageDto.setOrders(new HashSet<>());
        carriageDto.setRoutesForCarriageSet(new HashSet<>());
        Carriage forSave = new Carriage();
        forSave.setOrders(new HashSet<>());
        forSave.setRoutesForCarriageSet(new HashSet<>());
        when(carriageRepository.save(any(Carriage.class))).thenReturn(forSave);
        ResponseEntity<CarriageDto> savedCarriage = carriageController.newCarriage(carriageDto);
        System.out.println(savedCarriage);
        assertNotNull(savedCarriage);
    }

    @Test
    void updateCarriage() throws ParseException {
        CarriageDto carriageDto = new CarriageDto();
        carriageDto.setOrders(new HashSet<>());
        carriageDto.setRoutesForCarriageSet(new HashSet<>());
        carriageDto.setId(6L);
        Carriage forSave = new Carriage();
        forSave.setOrders(new HashSet<>());
        forSave.setRoutesForCarriageSet(new HashSet<>());
        when(carriageRepository.save(any(Carriage.class))).thenReturn(forSave);
        when(carriageRepository.findById(6L)).thenReturn(Optional.of(new Carriage())
                .map(carriage -> {
                    carriage.setId(6L);
                    return carriage;
                }));
        ResponseEntity<CarriageDto> savedCarriage = carriageController.updateCarriage(carriageDto, 6L);
        System.out.println(savedCarriage);
        assertNotNull(savedCarriage);
    }

    @Test
    void deleteCarriage() {
        ResponseEntity<Void> delResult = carriageController.deleteCarriage(1L);
        System.out.println(delResult);
        assertNotNull(delResult);
    }
}