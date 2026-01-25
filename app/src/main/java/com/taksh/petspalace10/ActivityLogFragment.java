package com.taksh.petspalace10;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class ActivityLogFragment extends Fragment {

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_log, container, false);
        recyclerView = view.findViewById(R.id.rvActivityLog);

        view.findViewById(R.id.btnBackLog).setOnClickListener(v -> getParentFragmentManager().popBackStack());

        setupActivities();
        return view;
    }

    private void setupActivities() {
        List<ActivityLogModel> list = new ArrayList<>();

        // Past Activities (Blue Dots)
        list.add(new ActivityLogModel("Morning Walk", "07:00 AM", "30 mins", R.drawable.ic_log, Color.parseColor("#4CAF50"), true));
        list.add(new ActivityLogModel("Breakfast Time", "08:00 AM", "15 mins", R.drawable.ic_food, Color.parseColor("#FF9800"), true));
        list.add(new ActivityLogModel("Teeth Cleaning", "08:45 AM", "5 mins", R.drawable.ic_health, Color.parseColor("#00BCD4"), true));

        // Upcoming Activities (Grey Dots)
        list.add(new ActivityLogModel("Disk Throw Training", "10:30 AM", "20 mins", R.drawable.ic_paw, Color.parseColor("#8BC34A"), false));
        list.add(new ActivityLogModel("Fresh Water Refill", "12:00 PM", "2 mins", R.drawable.ic_health, Color.parseColor("#2196F3"), false));
        list.add(new ActivityLogModel("Afternoon Power Nap", "01:30 PM", "90 mins", R.drawable.ic_home, Color.parseColor("#9C27B0"), false));
        list.add(new ActivityLogModel("Grooming & Brushing", "04:00 PM", "25 mins", R.drawable.ic_health, Color.parseColor("#E91E63"), false));
        list.add(new ActivityLogModel("Evening Park Walk", "06:30 PM", "45 mins", R.drawable.ic_log, Color.parseColor("#2E7D32"), false));
        list.add(new ActivityLogModel("Dinner Serving", "08:00 PM", "15 mins", R.drawable.ic_food, Color.parseColor("#FB8C00"), false));
        list.add(new ActivityLogModel("Night Sleep", "10:30 PM", "8 hours", R.drawable.ic_home, Color.parseColor("#3F51B5"), false));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ActivityLogAdapter(list));
    }
}