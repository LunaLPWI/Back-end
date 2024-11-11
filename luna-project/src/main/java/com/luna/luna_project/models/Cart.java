package com.luna.luna_project.models;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class Cart implements ICalculationValue{
    private String name;
    private ICalculationValue iCalculationValue;
    @Override
    public Double calculateValue(Double value) {
        return null;
    }
}
