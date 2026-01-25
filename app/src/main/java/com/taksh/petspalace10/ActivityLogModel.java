package com.taksh.petspalace10;

public class ActivityLogModel {
    private String title, timeSlot, duration;
    private int iconRes, iconBgColor;

    public ActivityLogModel(String title, String timeSlot, String duration, int iconRes, int iconBgColor) {
        this.title = title;
        this.timeSlot = timeSlot;
        this.duration = duration;
        this.iconRes = iconRes;
        this.iconBgColor = iconBgColor;
    }

    public String getTitle() { return title; }
    public String getTimeSlot() { return timeSlot; }
    public String getDuration() { return duration; }
    public int getIconRes() { return iconRes; }
    public int getIconBgColor() { return iconBgColor; }
}