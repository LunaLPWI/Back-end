package com.luna.luna_project.repositories;

import com.luna.luna_project.models.Plan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

import java.time.LocalDateTime;
import java.util.List;


public interface PlanRepository extends JpaRepository<Plan, Long> {
    Long countByName(String name);

    @Query("SELECT p FROM Plan p WHERE p.created_at BETWEEN :startDateTime AND :endDateTime")
    List<Plan> findPlanByCreatedAtBetween(
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime
    );

    Boolean existsByIdEstablishment(Long id);


    void deleteByIdEstablishment(Long idEstablishment);
}
