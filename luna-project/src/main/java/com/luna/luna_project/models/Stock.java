package com.luna.luna_project.models;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;

@Getter
@Setter
@AllArgsConstructor
public class Stock implements ICalculationValue{

    private Product products;

    @Override
    public Double calculateValue(Double value) {
        return null;
    }
}
