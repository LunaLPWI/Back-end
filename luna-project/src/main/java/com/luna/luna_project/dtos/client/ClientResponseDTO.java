package com.luna.luna_project.dtos.client;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;


@Builder
@Getter
@Setter
@Data
public class ClientResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private LocalDate birthDay;
    private Set<String> roles;
}



