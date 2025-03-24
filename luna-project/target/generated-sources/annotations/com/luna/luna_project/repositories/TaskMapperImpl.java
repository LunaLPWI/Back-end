package com.luna.luna_project.repositories;

import com.luna.luna_project.dtos.TaskDTO;
import com.luna.luna_project.enums.Task;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T21:21:52-0300",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public TaskDTO taskToTaskDTO(Task task) {
        if ( task == null ) {
            return null;
        }

        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setValue( task.getValue() );
        taskDTO.setDescription( task.getDescription() );
        taskDTO.setDuration( task.getDuration() );

        return taskDTO;
    }
}
