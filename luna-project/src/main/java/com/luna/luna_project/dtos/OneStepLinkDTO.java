package com.luna.luna_project.dtos;


import lombok.Data;

@Data
public class OneStepLinkDTO {
    private Long id;
    private Integer card_discount;
    private String payment_url;
    private ChargeRequestDTO chargeRequest;
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
