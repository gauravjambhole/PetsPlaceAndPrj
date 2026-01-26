package com.taksh.petspalace10;

public class MealModel {
    private String mealName, time, description;
    private boolean isLogged;

    public MealModel(String mealName, String time, String description, boolean isLogged) {
        this.mealName = mealName;
        this.time = time;
        this.description = description;
        this.isLogged = isLogged;
    }

    public String getMealName() { return mealName; }
    public String getTime() { return time; }
    public String getDescription() { return description; }
    public boolean isLogged() { return isLogged; }
    public void setLogged(boolean logged) { isLogged = logged; }
}