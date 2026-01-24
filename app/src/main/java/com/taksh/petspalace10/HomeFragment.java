package com.taksh.petspalace10;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private View headerSection, bannerCard, servicesTitle, gridLayout;
    private TextView tvUserDisplayName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // 1. Initialize Views
        headerSection = view.findViewById(R.id.headerSection);
        tvUserDisplayName = view.findViewById(R.id.tvUserDisplayName);
        bannerCard = view.findViewById(R.id.bannerCard);
        servicesTitle = view.findViewById(R.id.servicesTitle);
        gridLayout = view.findViewById(R.id.servicesGrid);

        // 2. Setup Clicks
        setupCardClicks(view);

        // 3. Start Animations (Exactly like LoginActivity)
        startEntranceAnimations();

        return view;
    }

    private void startEntranceAnimations() {
        if (getContext() == null) return;

        int duration = 800;

        // Set initial state: Hidden and pushed down
        headerSection.setAlpha(0f);
        headerSection.setTranslationY(dpToPx(30));

        bannerCard.setAlpha(0f);
        bannerCard.setTranslationY(dpToPx(50));

        servicesTitle.setAlpha(0f);
        servicesTitle.setTranslationY(dpToPx(40));

        gridLayout.setAlpha(0f);
        gridLayout.setTranslationY(dpToPx(60));

        // Start Pop-up animations with staggered delays
        headerSection.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(300).start();
        bannerCard.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(500).start();
        servicesTitle.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(600).start();
        gridLayout.animate().alpha(1f).translationY(0).setDuration(duration).setStartDelay(700).start();
    }

    // Fixed Helper: Accepts DP and returns calculated Pixels
    private float dpToPx(int dp) {
        if (getContext() == null) return dp;
        return dp * getResources().getDisplayMetrics().density;
    }

    private void setupCardClicks(View view) {
        view.findViewById(R.id.cardBookVet).setOnClickListener(v -> replaceFragment(new BookAppointmentFragment()));
        view.findViewById(R.id.cardFood).setOnClickListener(v -> replaceFragment(new FoodGuidenceFragment()));
        view.findViewById(R.id.cardActivityLog).setOnClickListener(v -> replaceFragment(new ActivityLogFragment()));
        view.findViewById(R.id.cardHealth).setOnClickListener(v -> replaceFragment(new HealthFragment()));
    }

    private void replaceFragment(Fragment fragment) {
        getParentFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.slide_in_right, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out_right)
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }
}