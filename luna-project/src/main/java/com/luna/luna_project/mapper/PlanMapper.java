package com.luna.luna_project.mapper;


import com.luna.luna_project.dtos.PlanDTO;
import com.luna.luna_project.models.Plan;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Map;

@Mapper(componentModel = "spring")
public interface PlanMapper {
    PlanMapper INSTANCE = Mappers.getMapper(PlanMapper.class);

    PlanDTO planToPlanDTO(Plan plan);

    Plan planDTOtoPlan(PlanDTO planDTO);



    default Plan mapToPlan(Map<String, Object> response) {
        Plan plan = new Plan();

        if (response.get("plan_id") != null) {
            plan.setPlan_id(response.get("plan_id").toString());
        }
        plan.setName((String) response.get("name"));
        if (response.get("interval") != null) {
            plan.setInterval(Integer.parseInt(response.get("interval").toString()));
        }
        if (response.get("repeats") != null) {
            plan.setRepeats(Integer.parseInt(response.get("repeats").toString()));
        }
        if (response.get("created_at") != null) {
            plan.setCreated_at(LocalDateTime.parse(response.get("created_at").toString().replace(" ", "T")));
        }

        return plan;
    }
}
