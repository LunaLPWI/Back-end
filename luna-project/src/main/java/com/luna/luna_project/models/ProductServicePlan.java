package com.luna.luna_project.models;

public class ProductServicePlan {
    private String name;
    private double price;

    // Construtores, getters e setters
    public ProductServicePlan(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
}
