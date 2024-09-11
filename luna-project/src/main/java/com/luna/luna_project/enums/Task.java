package com.luna.luna_project.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum Task {
    EYEBROW(30, "Design de sobrancelha", 15),
    HAIR(50, "Corte de cabelo", 30),
    BEARD(25, "Aparar barba", 20);

    private final Integer value;
    private final String description;
    private final Integer duration;
}
