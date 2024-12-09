package com.luna.luna_project.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "one_step_card")
@Data
public class OneStepCardSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String subscription_id;

    private Integer total;

    private String payment;

    @Column(name = "first_execution")
    private LocalDateTime first_execution;

    @OneToMany(mappedBy = "oneStepPlan", cascade = CascadeType.ALL, orphanRemoval = true)
    private  List<Plan> plans;

    private String status;

}
