package com.luna.luna_project.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data
@Table(name = "subscription_seq")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subscription_id")
    private String subscriptionId;

    private String custom_id;

    @Column(name = "created_at")
    private String created_at;

    private String status;

    @Column(name = "id_establishment")
    private Long idEstablishment;
}


