package com.luna.luna_project.services;

import com.luna.luna_project.dtos.CpfDTO;
import com.luna.luna_project.dtos.OneStepDTO;
import com.luna.luna_project.dtos.PlanDTO;
import com.luna.luna_project.gerencianet.subscription.json.PlanEFI;
import com.luna.luna_project.mapper.PlanMapper;
import com.luna.luna_project.models.Client;
import com.luna.luna_project.models.Establishment;
import com.luna.luna_project.models.Plan;
import com.luna.luna_project.repositories.PlanRepository;
import com.luna.luna_project.repositories.SubscriptionRepository;
import jakarta.transaction.Transactional;
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



    public PlanDTO savePlan(OneStepDTO request, Establishment establishment) {
        PlanDTO planDTO = request.getPlan();

        Boolean planBol = planRepository.existsByIdEstablishment(establishment.getId());

        if (planBol){
            return null;
        }

        Plan planDTOSaved = PlanEFI.createPlan(planDTO, request.getChargeRequest());
        if (planDTOSaved == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        planDTOSaved.setIdEstablishment(establishment.getId());

        Plan plan = planRepository.save(planDTOSaved);
        establishment.setPlan(plan);

        return planMapper.planToPlanDTO(plan);
    }

    public Long searchClientsByPlan(String name) {
        return planRepository.countByName(name);
    }

    @Transactional
    public String cancelPlan(CpfDTO cpfDto) {
        Client client = clientService.searchClientByCpf(cpfDto.getCpf());
        Establishment establishment = client.getEstablishment();

        String subscriptionId = subscriptionRepository.findSubscriptionIdByIdEstablishment(establishment.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "Não há nenhum cliente com esse id com plano"));

        String cancelSu = PlanEFI.cancelSubscription(subscriptionId);

        establishment.setPlan(null);

        subscriptionRepository.deleteBySubscriptionId(subscriptionId);
        planRepository.deleteByIdEstablishment(establishment.getId());

        return cancelSu;
    }


    public Long countPlan() {
        long id = subscriptionRepository.count();

        if (id == 0){
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Não há nenhum id");
        }
        return id;
    }
}
