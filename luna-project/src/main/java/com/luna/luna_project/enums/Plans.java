package com.luna.luna_project.enums;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Plans {

    PURPLE("Modulo agendamento", 1, 2000),

    GOLD("Agendamento e CRM", 1, 3000),

    PLATINUM("Agendamento CRM e Financeiro", 1, 5000);


    private final String name;
    private final int amount;
    private final int value;
}
