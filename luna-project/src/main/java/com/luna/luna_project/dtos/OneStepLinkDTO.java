package com.luna.luna_project.dtos;


import com.luna.luna_project.enums.Plans;
import lombok.Data;

@Data
public class OneStepLinkDTO {
    private Long id;
    private String payment_url;
    private Plans chargeRequest;
    private String created_at;
    private Integer subscription_id;
    private String expire_at;
    private Boolean request_delivery_address;
    private String payment_method;
    private String status;
}
