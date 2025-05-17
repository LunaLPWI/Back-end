package com.luna.luna_project.models;

import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Table(name = "one_step_link")
@Entity
public class OneStepLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer card_discount;
    private String payment_url;
    @OneToMany(mappedBy = "oneStepLink", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Charge> charges;
    private String custom_id;
    private String created_at;
    private String message;
    private Integer subscription_id;
    private Integer billet_discount;
    private String conditional_discount_date;
    private String expire_at;
    private Boolean request_delivery_address;
    private String payment_method;
    private String status;
}
