package com.luna.luna_project.dtos;

import lombok.Data;

import java.util.List;

@Data
public class SubscriptionDTO {

    private Long id;
    private String subscription_id;
    private List<ChargeDTO> charges;
    private String custom_id;
    private String created_at;
    private String status;
}

