package com.luna.luna_project.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
public enum Payment {
    DEBITO,
    CREDITO,
    PIX,
    DINHEIRO
}
