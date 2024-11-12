package com.luna.luna_project.repositories;

import com.luna.luna_project.dtos.TaskDTO;
import com.luna.luna_project.dtos.client.ClientDTO;
import com.luna.luna_project.enums.Task;
import com.luna.luna_project.models.Client;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskDTO taskToTaskDTO(Task task);
}
