package com.luna.luna_project.repositories;
import com.luna.luna_project.dtos.EmployeeServiceCount;
import com.luna.luna_project.models.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

    List<Scheduling> findSchedulingByClient_IdAndStartDateTimeBetween(Long clientId, LocalDateTime startDateTime,
                                                                      LocalDateTime endDateTime);

    List<Scheduling> findSchedulingByEmployee_IdAndStartDateTimeBetween(Long employeeId, LocalDateTime startDateTime,
                                                                        LocalDateTime endDateTime);
    List<Scheduling> findSchedulingByClient_IdAndStartDateTimeAfter(Long employeeId, LocalDateTime startDateTime);

    List<Scheduling> findSchedulingByStartDateTimeBetween(LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("""
        SELECT COALESCE(SUM(task.value), 0)
          FROM Scheduling s
          JOIN s.items task
          JOIN s.employee emp
          JOIN emp.establishments est
         WHERE :establishmentId = est.id
           AND 'ROLE_EMPLOYEE' IN ELEMENTS(emp.roles)
           AND s.startDateTime BETWEEN :startDateTime AND :endDateTime
    """)
    Double sumTotalServiceValuesByEstablishmentAndTimeRange(
            @Param("establishmentId") Long establishmentId,
            @Param("startDateTime")  LocalDateTime startDateTime,
            @Param("endDateTime")    LocalDateTime endDateTime
    );

    @Query("""
        SELECT 
          emp.id             AS employeeId,
          emp.name           AS employeeName,
          COUNT(task)        AS serviceCount
        FROM Scheduling s
        JOIN s.items task
        JOIN s.employee emp
        JOIN emp.establishments est
        WHERE est.id = :establishmentId
          AND 'ROLE_EMPLOYEE' IN ELEMENTS(emp.roles)
          AND s.startDateTime BETWEEN :startDateTime AND :endDateTime
        GROUP BY emp.id, emp.name
    """)
    List<EmployeeServiceCount> countServicesByEmployeeInPeriod(
            @Param("establishmentId") Long establishmentId,
            @Param("startDateTime")  LocalDateTime startDateTime,
            @Param("endDateTime")    LocalDateTime endDateTime
    );

    @Query("""
    SELECT COUNT(task)
      FROM Scheduling s
      JOIN s.items task
      JOIN s.employee emp
      JOIN emp.establishments est
     WHERE :establishmentId = est.id
       AND 'ROLE_EMPLOYEE' IN ELEMENTS(emp.roles)
       AND s.startDateTime BETWEEN :startDateTime AND :endDateTime
""")
    Long countTotalServicesByEstablishmentAndTimeRange(
            @Param("establishmentId") Long establishmentId,
            @Param("startDateTime")  LocalDateTime startDateTime,
            @Param("endDateTime")    LocalDateTime endDateTime
    );




    @Query("SELECT count(p) " +
            "FROM Scheduling s " +
            "JOIN s.items p " +
            "WHERE s.employee.id = :employeeId " +
            "AND s.startDateTime BETWEEN :startDate AND :endDate")
    Long sumServicesByEmployeeAndDateRange(@Param("employeeId") Long employeeId,
                                                 @Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);

    Optional<Scheduling> findTopByClient_IdOrderByStartDateTimeDesc(Long clientId);



}
