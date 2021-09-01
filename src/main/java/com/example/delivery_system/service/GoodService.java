package com.example.delivery_system.service;

import com.example.delivery_system.entity.Good;
import com.example.delivery_system.repository.GoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoodService {
    private final GoodRepository goodRepository;

    public Good findGoodById(Long goodId) {
        Optional<Good> goodFromDb = goodRepository.findById(goodId);
        return goodFromDb.orElse(new Good());
    }

    public Good update(Good newGood, Long oldId) {
        return goodRepository.findById(oldId).
                map(good -> {
                    good.setName(newGood.getName());
                    good.setVolume(newGood.getVolume());
                    good.setGoodsInOutlets(newGood.getGoodsInOutlets());
                    return goodRepository.save(good);
                })
                .orElseGet(() -> {
                    newGood.setId(oldId);
                    return goodRepository.save(newGood);
                });
    }

    public List<Good> allGoods() {
        return goodRepository.findAll(Sort.by("name"));
    }


    public Good save(Good newGood){
        return goodRepository.save(newGood);
    }

    public void deleteById(long goodId){
        goodRepository.deleteById(goodId);
    }
}
