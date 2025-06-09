package com.luna.luna_project.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Assessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assessment_id;
    @ManyToOne
    @JoinColumn(name = "establishment_id")
    private Establishment establishment;
    @ManyToOne
    @JoinColumn(name = "scheduling_id")
    private Scheduling scheduling;
    private Double rating;
    private String messaging;
}
