package com.taksh.petspalace10;

import android.os.CountDownTimer;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityTimerViewModel extends ViewModel {
    private final Map<String, MutableLiveData<Long>> timerStates = new HashMap<>();
    private final Map<String, CountDownTimer> activeTimers = new HashMap<>();

    private List<ActivityLogModel> activityList = null;
    private List<MealModel> mealList = null;

    public List<ActivityLogModel> getActivityList() { return activityList; }
    public void setActivityList(List<ActivityLogModel> list) { this.activityList = list; }

    public List<MealModel> getMealList() { return mealList; }
    public void setMealList(List<MealModel> list) { this.mealList = list; }

    public MutableLiveData<Long> getTimerValue(String title) {
        if (!timerStates.containsKey(title)) {
            timerStates.put(title, new MutableLiveData<>(0L));
        }
        return timerStates.get(title);
    }

    public void startTimer(String title, long minutes) {
        if (activeTimers.containsKey(title)) return;
        CountDownTimer timer = new CountDownTimer(minutes * 60000, 1000) {
            @Override
            public void onTick(long ms) { getTimerValue(title).postValue(ms); }
            @Override
            public void onFinish() {
                getTimerValue(title).postValue(-1L);
                activeTimers.remove(title);
            }
        }.start();
        activeTimers.put(title, timer);
    }
}