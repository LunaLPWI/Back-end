package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.PlanDTO;
import com.luna.luna_project.models.Plan;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-22T21:21:53-0300",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 22.0.2 (Oracle Corporation)"
)
@Component
public class PlanMapperImpl implements PlanMapper {

    @Override
    public PlanDTO planToPlanDTO(Plan plan) {
        if ( plan == null ) {
            return null;
        }

        PlanDTO planDTO = new PlanDTO();

        planDTO.setId( plan.getId() );
        planDTO.setName( plan.getName() );
        planDTO.setInterval( plan.getInterval() );
        planDTO.setRepeats( plan.getRepeats() );
        planDTO.setCreated_at( plan.getCreated_at() );
        planDTO.setPlan_id( plan.getPlan_id() );

        return planDTO;
    }

    @Override
    public Plan planDTOtoPlan(Plan planDTO) {
        if ( planDTO == null ) {
            return null;
        }

        Plan plan = new Plan();

        plan.setId( planDTO.getId() );
        plan.setName( planDTO.getName() );
        plan.setInterval( planDTO.getInterval() );
        plan.setRepeats( planDTO.getRepeats() );
        plan.setCreated_at( planDTO.getCreated_at() );
        plan.setPlan_id( planDTO.getPlan_id() );
        plan.setOneStepPlan( planDTO.getOneStepPlan() );
        plan.setIdClient( planDTO.getIdClient() );

        return plan;
    }
}
