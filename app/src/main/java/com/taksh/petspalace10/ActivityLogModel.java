package com.taksh.petspalace10;

public class ActivityLogModel {
    private String title, timeSlot, duration;
    private int iconRes, iconBgColor;
    private boolean isCompleted;

    public ActivityLogModel(String title, String timeSlot, String duration, int iconRes, int iconBgColor, boolean isCompleted) {
        this.title = title;
        this.timeSlot = timeSlot;
        this.duration = duration;
        this.iconRes = iconRes;
        this.iconBgColor = iconBgColor;
        this.isCompleted = isCompleted;
    }

    public String getTitle() { return title; }
    public String getTimeSlot() { return timeSlot; }
    public String getDuration() { return duration; }
    public int getIconRes() { return iconRes; }
    public int getIconBgColor() { return iconBgColor; }
    public boolean isCompleted() { return isCompleted; }
    public void setCompleted(boolean completed) { isCompleted = completed; }
}