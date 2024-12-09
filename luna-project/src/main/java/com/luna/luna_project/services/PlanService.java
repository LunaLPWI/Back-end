package com.luna.luna_project.services;

import com.luna.luna_project.dtos.CpfDTO;
import com.luna.luna_project.dtos.OneStepDTO;
import com.luna.luna_project.dtos.PlanDTO;
import com.luna.luna_project.gerencianet.subscription.json.PlanEFI;
import com.luna.luna_project.mapper.PlanMapper;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.models.Plan;
import com.luna.luna_project.repositories.PlanRepository;
import com.luna.luna_project.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class PlanService {
    @Autowired
    private ClientService clientService;
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private PlanMapper planMapper;
    @Autowired
    private SubscriptionRepository subscriptionRepository;


    public PlanDTO savePlan(OneStepDTO request, Long idClient) {
        PlanDTO planDTO = request.getPlan();

        Boolean planBol = planRepository.existsByIdClient(idClient);

        if (planBol){
            return null;
        }

        Plan planDTOSaved = PlanEFI.createPlan(planDTO, request.getChargeRequest());
        if (planDTOSaved == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Plan planMapp = planMapper.planDTOtoPlan(planDTOSaved);
        planMapp.setIdClient(idClient);

        Plan plan = planRepository.save(planMapp);

        return planMapper.planToPlanDTO(plan);
    }

    public Long searchClientsByPlan(String name) {
        return planRepository.countByName(name);
    }


    public String cancelPlan(CpfDTO cpfDto) {
        Client client = clientService.searchClientByCpf(cpfDto.getCpf());

        Long idClient = client.getId();

        String planId = subscriptionRepository.findSubscriptionIdByIdClient(idClient)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Não há nenhum cliente com esse id com plano"));

        return PlanEFI.cancelSubscription(planId);
    }


}
