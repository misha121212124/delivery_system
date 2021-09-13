package com.example.delivery_system.service;

import com.example.delivery_system.entity.Good;
import com.example.delivery_system.entity.Outlet;
import com.example.delivery_system.repository.GoodRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GoodServiceTest {
    @Mock
    GoodRepository goodRepository;

    GoodService goodService;

    @BeforeEach
    void setUp() {
        goodService = new GoodService(goodRepository);
    }

    @Test
    void findGoodById() {
        when(goodRepository.findById(1L)).thenReturn(Optional.of(new Good()));
        Good good = goodService.findGoodById(1L);
        assertNotNull(good);
    }

    @Test
    void update() {
        Good updateGood = new Good();
        updateGood.setId(8L);
        updateGood.setName("update test");
        when(goodRepository.save(any(Good.class))).thenReturn(updateGood);
        when(goodRepository.findById(8L)).thenReturn(Optional.of(new Good())
                .map(good -> {
                    good.setId(8L);
                    return good;
                }));
        updateGood = goodService.update(updateGood, 8L);
        assertNotNull(updateGood);
    }

    @Test
    void allGoods() {
        List<Good> list = new ArrayList<>();
        list.add(new Good());
        when(goodRepository.findAll(any(org.springframework.data.domain.Sort.class))).thenReturn(list);
        List goods = goodService.allGoods();
        assertNotNull(goods);
    }

    @Test
    void save() {
        Good saveGood = new Good();
        saveGood.setId(10L);
        saveGood.setName("save test");
        when(goodRepository.save(any(Good.class))).thenReturn(saveGood);
        saveGood = goodService.save(saveGood);
        assertNotNull(saveGood);
    }

    @Test
    void deleteById() {// what should I do with it?
        goodService.deleteById(2L);
        assert true;
    }
}