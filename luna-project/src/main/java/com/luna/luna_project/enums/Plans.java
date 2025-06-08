package com.luna.luna_project.enums;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Plans {

    PURPLE("Modulo agendamento", 1, 20),

    GOLD("Agendamento e CRM", 1, 30),

    PLATINUM("Agendamento CRM e Financeiro", 1, 50);


    private final String name;
    private final int amount;
    private final int value;
}
