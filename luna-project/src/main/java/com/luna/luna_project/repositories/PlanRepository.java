package com.luna.luna_project.repositories;

import com.luna.luna_project.models.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PlanRepository extends JpaRepository<Plan, Long> {
    Long countByName(String name);

    Boolean existsByIdClient(Long id);
}
