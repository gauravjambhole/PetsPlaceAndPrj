package com.taksh.petspalace10;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class BookAppointmentFragment extends Fragment {

    private String selectedDate = "";
    private String selectedTime = "";
    private List<Button> timeButtons;

    // Views for animation
    private View headerSection, calendarCard, timeLabel, timeGrid, btnConfirm;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book_appointment, container, false);

        // 1. Initialize Views
        headerSection = view.findViewById(R.id.headerSectionBook);
        calendarCard = view.findViewById(R.id.calendarCard);
        timeLabel = view.findViewById(R.id.timeLabel);
        timeGrid = view.findViewById(R.id.timeGrid);
        btnConfirm = view.findViewById(R.id.btnConfirmBooking);

        // 2. Back Button Logic
        view.findViewById(R.id.btnBack).setOnClickListener(v -> {
            getParentFragmentManager().popBackStack();
        });

        // 3. Calendar Logic
        CalendarView calendarView = view.findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((cv, year, month, dayOfMonth) -> {
            selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
        });

        // 4. Time Slot Setup
        timeButtons = new ArrayList<>();
        timeButtons.add(view.findViewById(R.id.time9am));
        timeButtons.add(view.findViewById(R.id.time11am));
        timeButtons.add(view.findViewById(R.id.time3pm));

        for (Button btn : timeButtons) {
            btn.setOnClickListener(v -> handleTimeSelection((Button) v));
        }

        // 5. Confirm Button Logic
        btnConfirm.setOnClickListener(v -> {
            if (selectedTime.isEmpty()) {
                Toast.makeText(getContext(), "Please select a time slot", Toast.LENGTH_SHORT).show();
            } else {
                String finalMessage = "Appointment set for " + (selectedDate.isEmpty() ? "Today" : selectedDate) + " at " + selectedTime;
                Toast.makeText(getContext(), finalMessage, Toast.LENGTH_LONG).show();
                getParentFragmentManager().popBackStack();
            }
        });

        // 6. Trigger Fast Entrance Animations
        startEntranceAnimations();

        return view;
    }

    private void startEntranceAnimations() {
        if (getContext() == null) return;

        // ANIMATION SETTINGS: Duration is cut in half for speed
        int duration = 400;
        int gap = 70; // Very short delay between items
        DecelerateInterpolator interpolator = new DecelerateInterpolator();

        // Step 1: Set starting positions (Low travel distance = Faster arrival)
        headerSection.setAlpha(0f); headerSection.setTranslationY(dpToPx(15));
        calendarCard.setAlpha(0f);  calendarCard.setTranslationY(dpToPx(25));
        timeLabel.setAlpha(0f);     timeLabel.setTranslationY(dpToPx(20));
        timeGrid.setAlpha(0f);      timeGrid.setTranslationY(dpToPx(25));
        btnConfirm.setAlpha(0f);    btnConfirm.setTranslationY(dpToPx(35));

        // Step 2: Animate with staggered "Waterfall" effect
        headerSection.animate().alpha(1f).translationY(0).setDuration(duration).setInterpolator(interpolator).setStartDelay(gap).start();
        calendarCard.animate().alpha(1f).translationY(0).setDuration(duration).setInterpolator(interpolator).setStartDelay(gap * 2).start();
        timeLabel.animate().alpha(1f).translationY(0).setDuration(duration).setInterpolator(interpolator).setStartDelay(gap * 3).start();
        timeGrid.animate().alpha(1f).translationY(0).setDuration(duration).setInterpolator(interpolator).setStartDelay(gap * 4).start();
        btnConfirm.animate().alpha(1f).translationY(0).setDuration(duration).setInterpolator(interpolator).setStartDelay(gap * 5).start();
    }

    private float dpToPx(int dp) {
        if (getContext() == null) return dp;
        return dp * getResources().getDisplayMetrics().density;
    }

    private void handleTimeSelection(Button selectedBtn) {
        for (Button btn : timeButtons) {
            btn.setBackgroundTintList(null);
            btn.setTextColor(ContextCompat.getColor(requireContext(), R.color.text_grey));
        }
        selectedBtn.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.primary_blue));
        selectedBtn.setTextColor(ContextCompat.getColor(requireContext(), R.color.white));
        selectedTime = selectedBtn.getText().toString();
    }
}