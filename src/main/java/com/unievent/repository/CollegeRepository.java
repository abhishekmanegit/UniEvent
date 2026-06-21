package com.unievent.repository;

import com.unievent.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollegeRepository extends JpaRepository<College, Long> {
    Boolean existsByName(String name);
}