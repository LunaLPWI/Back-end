package com.luna.luna_project.dtos.agendamentos;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SchedulingResponseAdminDTO {
    @NotBlank
    Long id;
    @Future
    private LocalDateTime startDateTime;
    @Future
    private LocalDateTime endDateTime;
    @NotBlank
    private String clientName;
}
