package com.example.delivery_system.controller;

import com.example.delivery_system.dto.OutletDto;
import com.example.delivery_system.entity.Outlet;
import com.example.delivery_system.repository.OutletRepository;
import com.example.delivery_system.service.OutletService;
import liquibase.pro.packaged.O;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OutletControllerTest {
    @Mock
    OutletRepository outletRepository;

    OutletController outletController;

    @BeforeEach
    void setUp() {
        outletController = new OutletController(new OutletService(outletRepository), new ModelMapper());
    }

    @Test
    void allOutlets() {
        List<Outlet> list = new ArrayList<>();
        Outlet testOutlet = new Outlet();
        testOutlet.setGoodsInOutletsSet(new ArrayList<>());
        list.add(testOutlet);
        when(outletRepository.findAll(any(org.springframework.data.domain.Sort.class))).thenReturn(list);
        ResponseEntity<List<OutletDto>> listDto = outletController.allOutlets();
        System.out.println(listDto);
        assertNotNull(listDto);
    }

    @Test
    void getById() {
        Outlet testOutlet = new Outlet();
        testOutlet.setGoodsInOutletsSet(new ArrayList<>());
        when(outletRepository.findById(1L)).thenReturn(Optional.of(testOutlet));
        ResponseEntity<OutletDto> outlet = outletController.getById(1L);
        System.out.println(outlet);
        assertNotNull(outlet);
    }

    @Test
    void newOutlet(){
        OutletDto outletDto = new OutletDto();
        outletDto.setGoodsInOutletsSet(new ArrayList<>());
        Outlet forSave = new Outlet();
        forSave.setGoodsInOutletsSet(new ArrayList<>());
        when(outletRepository.save(any(Outlet.class))).thenReturn(forSave);
        ResponseEntity<OutletDto> savedOutlet = outletController.newOutlet(outletDto);
        System.out.println(savedOutlet);
        assertNotNull(savedOutlet);
    }

    @Test
    void updateOutlet(){
        OutletDto outletDto = new OutletDto();
        outletDto.setGoodsInOutletsSet(new ArrayList<>());
        outletDto.setId(6L);
        Outlet forSave = new Outlet();
        forSave.setGoodsInOutletsSet(new ArrayList<>());
        when(outletRepository.save(any(Outlet.class))).thenReturn(forSave);
        when(outletRepository.findById(6L)).thenReturn(Optional.of(new Outlet())
                .map(outlet -> {
                    outlet.setId(6L);
                    return outlet;
                }));
        ResponseEntity<OutletDto> savedOutlet = outletController.updateOutlet(outletDto,6L);
        System.out.println(savedOutlet);
        assertNotNull(savedOutlet);
    }

    @Test
    void deleteOutlet() {
        ResponseEntity<Void> delResult = outletController.deleteOutlet(1L);
        System.out.println(delResult);
        assertNotNull(delResult);
    }
}