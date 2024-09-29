package com.luna.luna_project.models;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Plan plan;

    private String status;
    private LocalDate nextExecution;
    private LocalDate nextExpireAt;
    private String paymentMethod;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getNextExecution() {
        return nextExecution;
    }

    public void setNextExecution(LocalDate nextExecution) {
        this.nextExecution = nextExecution;
    }

    public LocalDate getNextExpireAt() {
        return nextExpireAt;
    }

    public void setNextExpireAt(LocalDate nextExpireAt) {
        this.nextExpireAt = nextExpireAt;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    // Getters and setters
}
