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

    @Query("SELECT c FROM Client c " +
            "WHERE EXISTS ( " +
            "    SELECT 1 FROM Scheduling s WHERE s.client = c " +
            "    AND s.startDateTime = ( " +
            "        SELECT MAX(s2.startDateTime) FROM Scheduling s2 WHERE s2.client = c " +
            "    ) " +
            "    AND s.startDateTime BETWEEN :startDate AND :endDate " +
            ") " +
            "AND :role NOT MEMBER OF c.roles")
    List<Client> findClientsWithRecentSchedulingBetweenDatesAndWithoutRole(@Param("startDate") LocalDateTime startDate,
                                                                           @Param("endDate") LocalDateTime endDate,
                                                                           @Param("role") String role);




    @Query(value = "SELECT COUNT(*) FROM client c " +
            "WHERE EXISTS ( " +
            "    SELECT 1 FROM scheduling s " +
            "    WHERE s.client_id = c.id " +
            "    AND s.start_date_time = ( " +
            "        SELECT MAX(s2.start_date_time) FROM scheduling s2 WHERE s2.client_id = c.id " +
            "    ) " +
            ") " +
            "AND EXISTS ( " +
            "    SELECT 1 FROM scheduling s1, scheduling s2 " +
            "    WHERE s1.client_id = c.id AND s2.client_id = c.id " +
            "    AND s1.start_date_time = ( " +
            "        SELECT MAX(s3.start_date_time) FROM scheduling s3 WHERE s3.client_id = c.id AND s3.start_date_time < s2.start_date_time " +
            "    ) " +
            "    AND DATEDIFF(s1.start_date_time, s2.start_date_time) BETWEEN :start AND :end " +
            ") " +
            "AND NOT EXISTS ( " +
            "    SELECT 1 FROM client_roles cr " +
            "    WHERE cr.client_id = c.id AND cr.roles = 'ROLE_EMPLOYEE' " +
            ")",
            nativeQuery = true)
    Long countClientsWithRecentSchedulingDifferenceBetween15And30DaysAndWithoutEmployeeRole(@Param("start")int startDate,
                                                                                            @Param("end") int start);



    @Query("SELECT count(p) " +
            "FROM Scheduling s " +
            "JOIN s.items p " +
            "WHERE s.employee.id = :employeeId " +
            "AND s.startDateTime BETWEEN :startDate AND :endDate")
    Long sumServicesByEmployeeAndDateRange(@Param("employeeId") Long employeeId,
                                                 @Param("startDate") LocalDateTime startDate,
                                                 @Param("endDate") LocalDateTime endDate);
}
