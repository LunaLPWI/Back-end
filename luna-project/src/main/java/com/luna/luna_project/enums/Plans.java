package com.luna.luna_project.enums;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Plans {

    PURPLE("Cabelo ou barba", 1, 20),

    GOLD("Corte de cabelo + barba", 1, 30),

    PLATINUM("Raspar a cabeça", 1, 50);


    private final String name;
    private final int amount;
    private final int value;
}
