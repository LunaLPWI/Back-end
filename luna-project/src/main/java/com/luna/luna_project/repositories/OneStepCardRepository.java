package com.luna.luna_project.repositories;

import com.luna.luna_project.models.OneStepCardSubscription;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OneStepCardRepository extends JpaRepository<OneStepCardSubscription, Long> {
}
