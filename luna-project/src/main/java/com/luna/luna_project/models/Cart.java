package com.luna.luna_project.models;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
@Data
public class Cart implements ICalculationValue{
    private String name;
    private ICalculationValue iCalculationValue;
    @Override
    public Double calculateValue(Double value) {
        return null;
    }
}
