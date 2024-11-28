package com.luna.luna_project.enums;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Plans {

    CORTE_OU_BARBA("Cabelo ou barba", 1, 13000),

    CORTE_CABELO_E_BARBA("Corte de cabelo + barba", 1, 20000),

    RASPAR_CABECA("Raspar a cabeça", 1, 10000),

    RASPAR_CABECA_E_BARBA("Raspar a cabeça + barba", 1, 18000),

    CORTE_CABELO_E_BARBA_2("Corte de cabelo + barba (2 cortes + 2 barbas)", 1, 13000);

    private final String name;
    private final int amount;
    private final int value;
}
