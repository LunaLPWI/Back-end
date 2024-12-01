package com.luna.luna_project.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class FrenquencyDTO {
    private long Frequentes ;
    private long medios;
    private long ocasionais;
}
