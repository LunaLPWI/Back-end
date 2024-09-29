package com.luna.luna_project.services;


import com.luna.luna_project.models.Subscription;
import com.luna.luna_project.repositories.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubscriptionService {
    @Autowired
    private SubscriptionRepository subscriptionRepository;

    public Subscription createSubscription(Subscription subscription) {
        return subscriptionRepository.save(subscription);
    }

    public List<Subscription> getAllSubscriptions() {
        return subscriptionRepository.findAll();
    }

    public Subscription updateSubscription(Long id, Subscription subscriptionDetails) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        subscription.setPlan(subscriptionDetails.getPlan());
        subscription.setStatus(subscriptionDetails.getStatus());
        subscription.setNextExecution(subscriptionDetails.getNextExecution());
        subscription.setNextExpireAt(subscriptionDetails.getNextExpireAt());
        subscription.setPaymentMethod(subscriptionDetails.getPaymentMethod());

        return subscriptionRepository.save(subscription);
    }

    public void cancelSubscription(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        subscription.setStatus("cancelled");
        subscriptionRepository.save(subscription);
    }
}