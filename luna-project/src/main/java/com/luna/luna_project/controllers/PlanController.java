package com.luna.luna_project.controllers;

import com.luna.luna_project.dtos.ChargeRequestDTO;
import com.luna.luna_project.dtos.PlanDTO;
import com.luna.luna_project.dtos.SubscriptionDTO;
import com.luna.luna_project.gerencianet.subscription.json.PlanEFI;
import com.luna.luna_project.mapper.PlanMapper;
import com.luna.luna_project.mapper.SubscriptionMapper;
import com.luna.luna_project.models.Charge;
import com.luna.luna_project.models.Plan;
import com.luna.luna_project.models.Subscription;
import com.luna.luna_project.repositories.ChargeRepository;
import com.luna.luna_project.repositories.PlanRepository;
import com.luna.luna_project.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/plans")
public class PlanController {
    @Autowired
    private PlanRepository planRepository;
    @Autowired
    private PlanMapper planMapper;
    @Autowired
    private SubscriptionRepository subscriptionRepository;
    @Autowired
    private SubscriptionMapper subscriptionMapper;

    @Autowired
    private ChargeRepository chargeRepository;


    @PostMapping("/create-plan")
    public ResponseEntity<PlanDTO> createPlan(@RequestBody PlanDTO planDTO) {

        Plan planDTOSaved = PlanEFI.createPlan(planDTO);
        if (planDTOSaved == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

        Plan savedPlan = planRepository.save(planMapper.planDTOtoPlan(planDTOSaved));
        return ResponseEntity.status(HttpStatus.CREATED).body(planMapper.planToPlanDTO(savedPlan));
    }


    @PostMapping("/create-charge")
    public ResponseEntity<SubscriptionDTO> createChargePlan(@RequestBody ChargeRequestDTO chargeRequestDTO) {
        Subscription subscription = PlanEFI.createCharge(chargeRequestDTO);

        Subscription savedSub = subscriptionRepository.save(subscription);

        SubscriptionDTO savedDTO = subscriptionMapper.subscriptionToSubscriptionDTO(savedSub);

        if (savedSub.getCharges() != null && !savedSub.getCharges().isEmpty()) {
            for (Charge charge : savedSub.getCharges()) {
                charge.setSubscription(savedSub);
            }
            chargeRepository.saveAll(savedSub.getCharges());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDTO);
    }


}
