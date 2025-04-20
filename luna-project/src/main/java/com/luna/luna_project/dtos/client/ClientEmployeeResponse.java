package com.luna.luna_project.dtos.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor

public class ClientEmployeeResponse {
        private String name;
        private String cpf;
        private String email;
        private String phoneNumber;
        private LocalDate birthDay;
        private String establishmentName;

}
