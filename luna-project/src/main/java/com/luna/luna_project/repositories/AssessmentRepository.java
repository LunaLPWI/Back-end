package com.luna.luna_project.repositories;

import com.luna.luna_project.models.Assessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AssessmentRepository extends JpaRepository<Assessment, Long> {
    List<Assessment> findByEstablishment_Id(Long establishmentId);

    @Query("SELECT AVG(a.rating) FROM Assessment a WHERE a.establishment.id = :establishmentId")
    Double findAverageRatingByEstablishmentId(@Param("establishmentId") Long establishmentId);

    @Query("SELECT a FROM Assessment a " +
            "JOIN a.scheduling s " +
            "WHERE s.client.id = :clientId " +
            "AND s.startDateTime < :currentDateTime and a.rating = null")
    List<Assessment> findAssessmentsByClientIdAndPastScheduling(@Param("clientId") Long clientId,
                                                                @Param("currentDateTime") LocalDateTime currentDateTime);

}
