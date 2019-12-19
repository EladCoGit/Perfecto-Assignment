package com.perfecto.assignment.db.structure;

import java.util.Date;

public class Entitlement {

    private int userId;
    private int numberOfSearches;
    private Date planStartDate;
    private Plan plan;

    public Entitlement(int userId, int numberOfSearches, Plan plan) {
        this.userId = userId;
        this.numberOfSearches = numberOfSearches;
        this.plan = plan;
        this.planStartDate = new Date();
    }

    public int getUserId() {
        return userId;
    }

    public int getNumberOfSearches() {
        return numberOfSearches;
    }

    public Date getPlanStartDate() {
        return planStartDate;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setNumberOfSearches(int numberOfSearches) {
        this.numberOfSearches = numberOfSearches;
    }

    public void increaseNumberOfSearches() {
        this.numberOfSearches++;
    }

    public void setPlanStartDate(Date planStartDate) {
        this.planStartDate = planStartDate;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }
}
