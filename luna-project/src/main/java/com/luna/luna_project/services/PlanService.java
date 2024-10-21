package com.luna.luna_project.services;
import com.luna.luna_project.dtos.PlanAndChargeRequestDTO;
import com.luna.luna_project.dtos.PlanDTO;
import com.luna.luna_project.gerencianet.subscription.json.PlanEFI;
import com.luna.luna_project.mapper.PlanMapper;
import com.luna.luna_project.models.Plan;
import com.luna.luna_project.repositories.PlanRepository;
import jakarta.validation.Valid;
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

    public Plan savePlan(@Valid PlanAndChargeRequestDTO request){
        PlanDTO planDTO = request.getPlanDTO();
        Plan planDTOSaved = PlanEFI.createPlan(planDTO);
        if (planDTOSaved == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        planRepository.save(planMapper.planDTOtoPlan(planDTOSaved));

        return planDTOSaved;
    }

}
