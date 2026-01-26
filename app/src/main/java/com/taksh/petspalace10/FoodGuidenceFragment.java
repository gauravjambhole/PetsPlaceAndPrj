package com.taksh.petspalace10;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class FoodGuidenceFragment extends Fragment implements MealAdapter.OnMealLoggedListener {

    private RecyclerView rvSchedule;
    private ActivityTimerViewModel viewModel;
    private TextView tvBanner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_guidence, container, false);

        rvSchedule = view.findViewById(R.id.rvDailySchedule);
        tvBanner = view.findViewById(R.id.bannerTitle);
        viewModel = new ViewModelProvider(requireActivity()).get(ActivityTimerViewModel.class);

        View backBtn = view.findViewById(R.id.btnBackFood);
        if (backBtn != null) {
            backBtn.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        setupData();
        checkAllMealsLogged(); // Initial check jab screen load ho
        return view;
    }

    private void setupData() {
        List<MealModel> list = viewModel.getMealList();

        if (list == null) {
            list = new ArrayList<>();
            list.add(new MealModel("Breakfast", "7:00 AM", "1 cup of Purina Pro Plan Kibble", false));
            list.add(new MealModel("Lunch", "1:00 PM", "1/2 cup wet food", false));
            list.add(new MealModel("Dinner", "8:00 PM", "1 cup kibble + supplements", false));
            viewModel.setMealList(list);
        }

        MealAdapter adapter = new MealAdapter(list, this);
        rvSchedule.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSchedule.setAdapter(adapter);
    }

    @Override
    public void onMealUpdate() {
        checkAllMealsLogged();
    }

    private void checkAllMealsLogged() {
        List<MealModel> list = viewModel.getMealList();
        if (list == null) return;

        boolean allDone = true;
        for (MealModel m : list) {
            if (!m.isLogged()) {
                allDone = false;
                break;
            }
        }

        if (allDone && tvBanner != null) {
            tvBanner.setText("Buddy's tummy is full! 🐶❤️");
        }
    }
}