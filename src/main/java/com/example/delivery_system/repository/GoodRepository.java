package com.example.delivery_system.repository;

import com.example.delivery_system.entity.Good;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoodRepository extends JpaRepository<Good, Long> {
}
