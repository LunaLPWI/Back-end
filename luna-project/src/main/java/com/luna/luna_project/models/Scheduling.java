package com.luna.luna_project.models;

import com.luna.luna_project.enums.Task;

import java.time.LocalDateTime;

public class Scheduling implements ICalculationValue {
    private LocalDateTime dayAndHour;
    private Task task;
    private Employee employee;


    @Override
    public Double calculateValue(Double value) {
        return null;
    }

    public LocalDateTime calculateEstimate(LocalDateTime tempoDuracao){
        return null;
    }
}
