package com.example.task.repository;

import com.example.task.entity.Costs;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CostsRepository extends JpaRepository <Costs, Long> {
    Optional <Costs> findByName(String name);
}
