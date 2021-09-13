package com.example.delivery_system.service;

import com.example.delivery_system.entity.Outlet;
import com.example.delivery_system.repository.OutletRepository;
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
class OutletServiceTest {
    @Mock
    OutletRepository outletRepository;

    OutletService outletService;

    @BeforeEach
    void setUp() {
        outletService = new OutletService(outletRepository);
    }

    @Test
    void findOutletById() {
        when(outletRepository.findById(1L)).thenReturn(Optional.of(new Outlet()));
        Outlet outlet = outletService.findOutletById(1L);
        assertNotNull(outlet);
    }

    @Test
    void update() {
        Outlet updateOutlet = new Outlet();
        updateOutlet.setId(8L);
        updateOutlet.setName("update test");
        when(outletRepository.save(any(Outlet.class))).thenReturn(updateOutlet);
        when(outletRepository.findById(8L)).thenReturn(Optional.of(new Outlet())
                .map(outlet -> {
                    outlet.setId(8L);
                    return outlet;
                }));
        updateOutlet = outletService.update(updateOutlet, 8L);
        assertNotNull(updateOutlet);
    }

    @Test
    void allOutlets() {
        List<Outlet> list = new ArrayList<>();
        list.add(new Outlet());
        when(outletRepository.findAll(any(org.springframework.data.domain.Sort.class))).thenReturn(list);
        List outlets = outletService.allOutlets();
        assertNotNull(outlets);
    }

    @Test
    void save() {
        Outlet saveOutlet = new Outlet();
        saveOutlet.setId(10L);
        saveOutlet.setName("save test");
        when(outletRepository.save(any(Outlet.class))).thenReturn(saveOutlet);
        saveOutlet = outletService.save(saveOutlet);
        assertNotNull(saveOutlet);
    }

    @Test
    void deleteById() {// what should I do with it?
        outletService.deleteById(2L);
        assert true;
    }
}