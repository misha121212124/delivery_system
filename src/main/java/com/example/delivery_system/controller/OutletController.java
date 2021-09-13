package com.example.delivery_system.controller;

import com.example.delivery_system.dto.GoodsInOutletsDto;
import com.example.delivery_system.dto.OutletDto;
import com.example.delivery_system.entity.Outlet;
import com.example.delivery_system.service.OutletService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/outlets")
public class OutletController {
    private final OutletService outletService;

    private final ModelMapper modelMapper;


    @GetMapping()
    public ResponseEntity<List<OutletDto>> allOutlets() {
        List<Outlet> outlets = outletService.allOutlets();
        return ResponseEntity.ok(outlets.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    ResponseEntity<OutletDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(convertToDto(outletService.findOutletById(id)));
    }

    @PostMapping()
    ResponseEntity<OutletDto> newOutlet(@RequestBody OutletDto newOutlet) throws ParseException {
        return ResponseEntity.status(HttpStatus.CREATED).body(convertToDto(outletService.save(convertToEntity(newOutlet))));
    }

    @PutMapping("/{id}")
    ResponseEntity<OutletDto> updateOutlet(@RequestBody OutletDto newOutlet, @PathVariable Long id) throws ParseException {
        return ResponseEntity.ok(convertToDto(outletService.update(convertToEntity(newOutlet), id)));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteOutlet(@PathVariable Long id) {
        outletService.deleteById(id);
        return ResponseEntity.noContent().build();

    }

    private OutletDto convertToDto(Outlet outlet) {
        OutletDto outletDto = modelMapper.map(outlet, OutletDto.class);
        for (GoodsInOutletsDto temp : outletDto.getGoodsInOutletsSet()) {
            temp.setOutletId(temp.getOutlet().getId());
            temp.setGoodId(temp.getGood().getId());
        }
        return outletDto;
    }

    private Outlet convertToEntity(OutletDto outletDto) throws ParseException {
        Outlet outlet = modelMapper.map(outletDto, Outlet.class);
        return outlet;
    }
}
