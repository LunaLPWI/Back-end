package com.luna.luna_project.services;

import com.luna.luna_project.mapper.SubscriptionMapper;
import com.luna.luna_project.models.OneStepLink;
import com.luna.luna_project.models.Subscription;
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

    public void saveCharge(@Valid OneStepLink request, Long idClient) {

        Subscription subscription = new Subscription();

        subscription.setSubscription_id(request.getSubscription_id().toString());
        subscription.setCreated_at(request.getCreated_at());
        subscription.setStatus(request.getStatus());
        subscription.setIdClient(idClient);

        subscriptionRepository.save(subscription);
    }

}
