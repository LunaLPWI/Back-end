package com.luna.luna_project.models;


import jakarta.persistence.*;
import lombok.*;

import java.sql.Time;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "establishment")

public class Establishment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String name;
    @ManyToOne
    @JoinColumn(name = "plan_id_plan", nullable = true)
    private Plan plan;
    private String cnpj;
    private double lat;
    private double lng;
    private Time openHour;
    private Time closeHour;
    private Boolean favorite;
    @ManyToOne
    private Address address;

}
