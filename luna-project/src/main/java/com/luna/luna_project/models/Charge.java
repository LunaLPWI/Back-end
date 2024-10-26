package com.luna.luna_project.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "charge")
@Data
public class Charge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer total;
    private Integer parcel;
    private String  charge_id;
    private String status;
    @ManyToOne
    private Subscription subscription;
}

