package com.luna.luna_project.repositories;

import com.luna.luna_project.models.Scheduling;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface SchedulingRepository extends JpaRepository<Scheduling, Long> {

    List<Scheduling> findSchedulingByClient_IdAndStartDateTimeBetween(Long clientId, LocalDateTime startDateTime,
                                                                      LocalDateTime endDateTime);

    List<Scheduling> findSchedulingByEmployee_IdAndStartDateTimeBetween(Long employeeId, LocalDateTime startDateTime,
                                                                        LocalDateTime endDateTime);
    List<Scheduling> findSchedulingByClient_IdAndStartDateTimeAfter(Long employeeId, LocalDateTime startDateTime);



}
