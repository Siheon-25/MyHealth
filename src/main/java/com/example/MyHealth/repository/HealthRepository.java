package com.example.MyHealth.repository;

import com.example.MyHealth.entity.Health;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

public interface HealthRepository extends CrudRepository<Health,Long> {
    ArrayList<Health> findAll();
}
