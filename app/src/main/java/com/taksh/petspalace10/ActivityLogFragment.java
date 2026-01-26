package com.taksh.petspalace10;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ActivityLogFragment extends Fragment implements ActivityLogAdapter.OnProgressListener {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private TextView tvPercent;
    private List<ActivityLogModel> list;
    private ActivityLogAdapter adapter;
    private ActivityTimerViewModel timerViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_log, container, false);

        recyclerView = view.findViewById(R.id.rvActivityLog);
        progressBar = view.findViewById(R.id.dailyProgressBar);
        tvPercent = view.findViewById(R.id.tvProgressPercent);

        // IMPORTANT: Use requireActivity() so the ViewModel is shared across fragments
        timerViewModel = new ViewModelProvider(requireActivity()).get(ActivityTimerViewModel.class);

        view.findViewById(R.id.btnBackLog).setOnClickListener(v -> getParentFragmentManager().popBackStack());

        setupActivities();
        updateDailyProgress();

        return view;
    }

    private void setupActivities() {
        // 1. Check if ViewModel already has the list
        list = timerViewModel.getActivityList();

        // 2. If it's null (first time opening the app), create it
        if (list == null) {
            list = new ArrayList<>();
            list.add(new ActivityLogModel("Morning Walk", "07:00 AM", "30 mins", R.drawable.ic_log, android.graphics.Color.parseColor("#4CAF50"), false));
            list.add(new ActivityLogModel("Breakfast", "08:00 AM", "15 mins", R.drawable.ic_food, android.graphics.Color.parseColor("#FF9800"), false));
            list.add(new ActivityLogModel("Teeth Cleaning", "08:45 AM", "5 mins", R.drawable.ic_health, android.graphics.Color.parseColor("#00BCD4"), false));
            list.add(new ActivityLogModel("Disk Training", "10:30 AM", "20 mins", R.drawable.ic_paw, android.graphics.Color.parseColor("#8BC34A"), false));
            list.add(new ActivityLogModel("Water Refill", "12:00 PM", "2 mins", R.drawable.ic_health, android.graphics.Color.parseColor("#2196F3"), false));
            list.add(new ActivityLogModel("Nap Time", "01:30 PM", "90 mins", R.drawable.ic_home, android.graphics.Color.parseColor("#9C27B0"), false));
            list.add(new ActivityLogModel("Grooming", "04:00 PM", "25 mins", R.drawable.ic_health, android.graphics.Color.parseColor("#E91E63"), false));
            list.add(new ActivityLogModel("Park Walk", "06:30 PM", "45 mins", R.drawable.ic_log, android.graphics.Color.parseColor("#2E7D32"), false));
            list.add(new ActivityLogModel("Dinner", "08:00 PM", "15 mins", R.drawable.ic_food, android.graphics.Color.parseColor("#FB8C00"), false));
            list.add(new ActivityLogModel("Night Sleep", "10:30 PM", "8 hours", R.drawable.ic_home, android.graphics.Color.parseColor("#3F51B5"), false));

            // Save it to ViewModel so it's never lost again
            timerViewModel.setActivityList(list);
        }

        adapter = new ActivityLogAdapter(list, this, timerViewModel);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onProgressUpdate() {
        updateDailyProgress();
    }

    private void updateDailyProgress() {
        int completed = 0;
        for (ActivityLogModel m : list) {
            if (m.isCompleted()) completed++;
        }
        int progress = (completed * 100) / list.size();
        progressBar.setProgress(progress);
        tvPercent.setText(progress + "%");
    }
}