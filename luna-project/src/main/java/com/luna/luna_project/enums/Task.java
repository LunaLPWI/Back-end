package com.luna.luna_project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum Task {
    CORTE(50.0, "Corte de cabelo", 45),
    BARBA(40.0, "Aparar barba", 45),
    BOTOX(65.0,"botox",45),
    HIDRATACAO(40.0,"Hidratação no cabelo",45),
    PEZINHOCABELOBARBA(30.0, "Pezinho cabelo e barba",45),
    PEZINHO(15.0,"Pezinho do cabelo",45),
    PLATINADOCORTE(120.0,"Platinar e cortar o cabelo",45),
    RASPARCABECA(27.0,"Raspar a cabeça", 45),
    SOBRANCELHA(20.0,"Fazer a sobrancelha", 45),
    RELAXAMENTO(20.0,"Relaxamento no cabelo",45);


    private final Double value;
    private final String description;
    private final Integer duration;


}
