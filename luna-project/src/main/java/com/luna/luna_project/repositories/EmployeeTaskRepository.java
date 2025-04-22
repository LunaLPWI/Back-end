package com.luna.luna_project.repositories;

import com.luna.luna_project.models.EmployeeTask;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeTaskRepository extends JpaRepository<EmployeeTask, Long> {
    List<EmployeeTask> findByClientId(Long clientId);
}
