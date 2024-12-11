package com.luna.luna_project.dtos.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@Data
@AllArgsConstructor
public class ClientOverviewDTO {
    private String name;
    private String planName;
    private LocalDateTime expireAt;
    private String status;
    private String phoneNumber;
}
