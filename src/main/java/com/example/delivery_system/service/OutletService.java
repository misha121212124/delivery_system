package com.example.delivery_system.service;

import com.example.delivery_system.entity.GoodsInOutlets;
import com.example.delivery_system.entity.Outlet;
import com.example.delivery_system.repository.OutletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OutletService {


    private final OutletRepository outletRepository;

    public Outlet findOutletById(Long outletId) {
        Optional<Outlet> outletFromDb = outletRepository.findById(outletId);
        return outletFromDb.orElse(new Outlet());
    }

    public Outlet update(Outlet newOutlet, Long oldId) {
        return outletRepository.findById(oldId).
                map(outlet -> {
                    outlet.setName(newOutlet.getName());
                    outlet.setGoodsInOutletsSet(newOutlet.getGoodsInOutletsSet());
                    return outletRepository.save(outlet);
                })
                .orElseGet(() -> {
                    newOutlet.setId(oldId);
                    return outletRepository.save(newOutlet);
                });
    }

    public List<Outlet> allOutlets() {
        return outletRepository.findAll(Sort.by("name"));
    }


    public Outlet save(Outlet newOutlet){
        return outletRepository.save(newOutlet);
    }

    public void deleteById(long outletId){
        outletRepository.deleteById(outletId);
    }

}
