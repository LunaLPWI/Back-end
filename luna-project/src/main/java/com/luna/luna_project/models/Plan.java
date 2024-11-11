package com.luna.luna_project.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "plan_seq")
public class Plan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "intevalDays")
    private Integer interval;
    private Integer repeats;
    @Column(name = "created_at")
    private LocalDateTime created_at;
    private String plan_id;
    @ManyToOne
    private OneStepCardSubscription oneStepPlan;
}
