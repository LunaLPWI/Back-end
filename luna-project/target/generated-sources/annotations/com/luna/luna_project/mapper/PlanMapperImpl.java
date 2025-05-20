package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.PlanDTO;
import com.luna.luna_project.models.Plan;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-19T21:44:54-0300",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 21.0.5 (Amazon.com Inc.)"
)
@Component
public class PlanMapperImpl implements PlanMapper {

    @Override
    public PlanDTO planToPlanDTO(Plan plan) {
        if ( plan == null ) {
            return null;
        }

        PlanDTO.PlanDTOBuilder planDTO = PlanDTO.builder();

        planDTO.id( plan.getId() );
        planDTO.name( plan.getName() );
        planDTO.interval( plan.getInterval() );
        planDTO.repeats( plan.getRepeats() );
        planDTO.created_at( plan.getCreated_at() );
        planDTO.plan_id( plan.getPlan_id() );

        return planDTO.build();
    }

    @Override
    public Plan planDTOtoPlan(PlanDTO planDTO) {
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

        return plan;
    }
}
