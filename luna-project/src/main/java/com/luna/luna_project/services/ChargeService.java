package com.luna.luna_project.services;
import com.luna.luna_project.dtos.ChargeRequestDTO;
import com.luna.luna_project.dtos.PlanAndChargeRequestDTO;
import com.luna.luna_project.dtos.SubscriptionDTO;
import com.luna.luna_project.gerencianet.subscription.json.PlanEFI;
import com.luna.luna_project.mapper.SubscriptionMapper;
import com.luna.luna_project.models.Plan;
import com.luna.luna_project.models.Subscription;
import com.luna.luna_project.repositories.ChargeRepository;
import com.luna.luna_project.repositories.SubscriptionRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ChargeService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private SubscriptionMapper subscriptionMapper;

    public SubscriptionDTO saveCharge(@Valid PlanAndChargeRequestDTO request, Plan plan){
        ChargeRequestDTO chargeRequestDTO = request.getChargeRequestDTO();

        Subscription subscription = PlanEFI.createCharge(chargeRequestDTO, plan);

        if (subscription == null){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Subscription savedSub = subscriptionRepository.save(subscription);
        return subscriptionMapper.subscriptionToSubscriptionDTO(savedSub);
    }

}
