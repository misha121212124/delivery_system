package com.example.delivery_system.service;

import com.example.delivery_system.entity.Outlet;
import com.example.delivery_system.repository.OutletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OutletService {


    private final OutletRepository outletRepository;

    public Outlet findOutletById(Long outletId) {
        Optional<Outlet> outletFromDb = outletRepository.findById(outletId);
        if(outletFromDb.isEmpty()) throw new NoSuchElementException("There is no such outlet with id = "+ outletId);
        return outletFromDb.get();
    }

    public Outlet update(Outlet newOutlet, Long oldId) {
        return outletRepository.findById(oldId).
                map(outlet -> {
                    outlet.setName(newOutlet.getName());
                    outlet.setGoodsInOutlets(newOutlet.getGoodsInOutlets());
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

    public List<Outlet> allOutletsSortedById() {
        return outletRepository.findAll(Sort.by("id"));
    }

    public Outlet save(Outlet newOutlet){
        return outletRepository.save(newOutlet);
    }

    public void deleteById(long outletId){
        outletRepository.deleteById(outletId);
    }

}
