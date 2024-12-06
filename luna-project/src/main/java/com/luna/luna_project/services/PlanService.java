package com.luna.luna_project.services;

import com.luna.luna_project.dtos.OneStepDTO;
import com.luna.luna_project.dtos.PlanDTO;
import com.luna.luna_project.enums.Plans;
import com.luna.luna_project.gerencianet.subscription.json.PlanEFI;
import com.luna.luna_project.mapper.PlanMapper;
import com.luna.luna_project.models.Plan;
import com.luna.luna_project.repositories.PlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PlanService {

    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private PlanMapper planMapper;


    public PlanDTO savePlan(OneStepDTO request, Long id) {
        PlanDTO planDTO = request.getPlan();
        Plan planDTOSaved = PlanEFI.createPlan(planDTO, request.getChargeRequest());
        if (planDTOSaved == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Plan planMapp = planMapper.planDTOtoPlan(planDTOSaved);
        planMapp.setId_client(id);

        Plan plan = planRepository.save(planMapp);

        return planMapper.planToPlanDTO(plan);
    }

    public Long searchClientsByPlan(String name){
        return planRepository.countByName(name);
    }

}
