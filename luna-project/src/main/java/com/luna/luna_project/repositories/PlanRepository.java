package com.luna.luna_project.repositories;

import com.luna.luna_project.models.Plan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanRepository extends JpaRepository<Plan, Long> {
}
