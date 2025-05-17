package com.luna.luna_project.mapper;

import com.luna.luna_project.dtos.PlanDTO;
import com.luna.luna_project.models.Plan;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-05-13T19:37:52-0300",
    comments = "version: 1.5.0.Final, compiler: javac, environment: Java 21.0.5 (Eclipse Adoptium)"
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
