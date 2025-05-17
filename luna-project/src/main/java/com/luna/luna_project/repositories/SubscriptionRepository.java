package com.luna.luna_project.repositories;

import com.luna.luna_project.models.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {



    @Query("SELECT s.subscriptionId FROM Subscription s WHERE s.idEstablishment = :establishmentId")
    Optional<String> findSubscriptionIdByIdEstablishment(Long establishmentId);
    void deleteBySubscriptionId(String subscriptionId);

}

