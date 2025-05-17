package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.EmployeeTask.EmployeeTaskDTO;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.models.EmployeeTask;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeTaskMapper {

    EmployeeTaskDTO toDTO(EmployeeTask task);
}
