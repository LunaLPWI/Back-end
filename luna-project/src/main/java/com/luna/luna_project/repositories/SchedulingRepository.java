package com.luna.luna_project.repositories;
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

    @Query("SELECT SUM(size(s.items)) FROM Scheduling s " +
            "WHERE s.employee.id IN (" +
            "   SELECT e.id FROM Client e JOIN e.roles r " +
            "   WHERE r = 'ROLE_EMPLOYEE' " +
            "   AND :establishmentId IN (SELECT est.id FROM Client c JOIN c.establishments est WHERE c.id = e.id)" +
            ") " +
            "AND s.startDateTime BETWEEN :startDateTime AND :endDateTime")
    Long countTotalServicesByEstablishmentAndTimeRange(
            @Param("establishmentId") Long establishmentId,
            @Param("startDateTime") LocalDateTime startDateTime,
            @Param("endDateTime") LocalDateTime endDateTime);



    @Query("SELECT count(p) " +
            "FROM Scheduling s " +
            "JOIN s.items p " +
            "WHERE s.employee.id = :employeeId " +
            "AND s.startDateTime BETWEEN :startDate AND :endDate")
    Long sumServicesByEmployeeAndDateRange(@Param("employeeId") Long employeeId,
                                                 @Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);



}
