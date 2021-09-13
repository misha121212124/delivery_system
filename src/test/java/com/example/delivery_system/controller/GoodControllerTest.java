package com.example.delivery_system.controller;

import com.example.delivery_system.dto.GoodDto;
import com.example.delivery_system.entity.Good;
import com.example.delivery_system.repository.GoodRepository;
import com.example.delivery_system.service.GoodService;
import com.example.delivery_system.service.GoodService;
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
class GoodControllerTest {
    @Mock
    GoodRepository goodRepository;

    GoodController goodController;

    @BeforeEach
    void setUp() {
        goodController = new GoodController(new GoodService(goodRepository), new ModelMapper());
    }

    @Test
    void allGoods() {
        List<Good> list = new ArrayList<>();
        Good testGood = new Good();
        testGood.setGoodsInOutlets(new ArrayList<>());
        list.add(testGood);
        when(goodRepository.findAll(any(org.springframework.data.domain.Sort.class))).thenReturn(list);
        ResponseEntity<List<GoodDto>> listDto = goodController.allGoods();
        System.out.println(listDto);
        assertNotNull(listDto);
    }

    @Test
    void getById() {
        Good testGood = new Good();
        testGood.setGoodsInOutlets(new ArrayList<>());
        when(goodRepository.findById(1L)).thenReturn(Optional.of(testGood));
        ResponseEntity<GoodDto> good = goodController.getById(1L);
        System.out.println(good);
        assertNotNull(good);
    }

    @Test
    void newGood() throws ParseException {
        GoodDto goodDto = new GoodDto();
        goodDto.setGoodsInOutlets(new ArrayList<>());
        Good forSave = new Good();
        forSave.setGoodsInOutlets(new ArrayList<>());
        when(goodRepository.save(any(Good.class))).thenReturn(forSave);
        ResponseEntity<GoodDto> savedGood = goodController.newGood(goodDto);
        System.out.println(savedGood);
        assertNotNull(savedGood);
    }

    @Test
    void updateGood() throws ParseException {
        GoodDto goodDto = new GoodDto();
        goodDto.setGoodsInOutlets(new ArrayList<>());
        goodDto.setId(6L);
        Good forSave = new Good();
        forSave.setGoodsInOutlets(new ArrayList<>());
        when(goodRepository.save(any(Good.class))).thenReturn(forSave);
        when(goodRepository.findById(6L)).thenReturn(Optional.of(new Good())
                .map(good -> {
                    good.setId(6L);
                    return good;
                }));
        ResponseEntity<GoodDto> savedGood = goodController.updateGood(goodDto,6L);
        System.out.println(savedGood);
        assertNotNull(savedGood);
    }

    @Test
    void deleteGood() {
        ResponseEntity<Void> delResult = goodController.deleteGood(1L);
        System.out.println(delResult);
        assertNotNull(delResult);
    }
}