package com.luna.luna_project.repositories;

import com.luna.luna_project.models.Client;
import com.luna.luna_project.models.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

    List<Scheduling> findSchedulingByClient_IdAndStartDateTimeBetween(Long clientId, LocalDateTime startDateTime,
                                                                      LocalDateTime endDateTime);

    List<Scheduling> findSchedulingByEmployee_IdAndStartDateTimeBetween(Long employeeId, LocalDateTime startDateTime,
                                                                        LocalDateTime endDateTime);
    List<Scheduling> findSchedulingByClient_IdAndStartDateTimeAfter(Long employeeId, LocalDateTime startDateTime);

    List<Scheduling> findSchedulingByStartDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("SELECT COUNT(c) FROM Client c WHERE NOT EXISTS (" +
            "SELECT s FROM Scheduling s WHERE s.client = c AND s.startDateTime = (" +
            "SELECT MAX(s2.startDateTime) FROM Scheduling s2 WHERE s2.client = c) " +
            "AND s.startDateTime >= :date) " +
            "AND :role NOT MEMBER OF c.roles")
    long countClientsWithoutRecentSchedulingAndNoEmployeeRole(@Param("date") LocalDateTime date, @Param("role") String role);


    @Query("SELECT SUM(p.amount) " +
            "FROM Scheduling s " +
            "JOIN s.products p " +
            "WHERE s.employee.id = :employeeId " +
            "AND s.startDateTime BETWEEN :startDate AND :endDate")
    long sumProductAmountsByEmployeeAndDateRange(@Param("employeeId") Long employeeId,
                                                 @Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);
}
