package com.luna.luna_project.dtos;
import lombok.Data;

@Data
public class ChargeDTO {
    private Integer total;
    private Integer parcel;
    private Long charge_id;
    private String status;
}

