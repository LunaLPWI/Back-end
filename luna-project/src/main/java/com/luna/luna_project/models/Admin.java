package com.luna.luna_project.models;

public class Admin extends Employee{
    private String name;


    public Stock addProduct(Product product){
        return null;
    }

    public Stock removeProduct(){
        return null;
    }

    @Override
    public void showFeedback(FeedBack feedBack) {
        super.showFeedback(feedBack);
    }

    @Override
    public Scheduling toSchedule() {
        return super.toSchedule();
    }

    @Override
    public Scheduling unSchedule() {
        return super.unSchedule();
    }
}
