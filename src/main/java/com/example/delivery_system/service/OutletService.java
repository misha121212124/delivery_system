package com.example.delivery_system.service;

import com.example.delivery_system.entity.Outlet;
import com.example.delivery_system.repository.OutletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OutletService {

//    public OutletService(OutletRepository outletRepository) {
//        this.outletRepository = outletRepository;
//    }

//    @Autowired

    private final OutletRepository outletRepository;

    public Outlet findOutletById(Long outletId) {
        Optional<Outlet> cargoFromDb = outletRepository.findById(outletId);
        return cargoFromDb.orElse(new Outlet());
    }

    public List<Outlet> allOutlets() {
        return outletRepository.findAll(Sort.by("name"));
    }


    public Outlet save(Outlet newOutlet){
        return outletRepository.save(newOutlet);
    }

    public void deleteById(long id){
        outletRepository.deleteById(id);
    }
}
