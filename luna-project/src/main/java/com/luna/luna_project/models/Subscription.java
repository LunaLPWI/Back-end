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

    private String subscription_id;

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Charge> charges;

    private String custom_id;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    private String status;
}

