package com.taksh.petspalace10;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class MealAdapter extends RecyclerView.Adapter<MealAdapter.ViewHolder> {
    private List<MealModel> mealList;
    private OnMealLoggedListener listener;

    public interface OnMealLoggedListener { void onMealUpdate(); }

    public MealAdapter(List<MealModel> mealList, OnMealLoggedListener listener) {
        this.mealList = mealList;
        this.listener = listener;
    }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup p, int viewType) {
        View v = LayoutInflater.from(p.getContext()).inflate(R.layout.item_meal_card, p, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        MealModel m = mealList.get(pos);
        h.tvTitle.setText(m.getTime() + " - " + m.getMealName());
        h.tvDesc.setText(m.getDescription());

        if (m.isLogged()) {
            h.btnLog.setText("LOGGED");
            h.btnLog.setBackgroundColor(Color.GRAY);
            h.btnLog.setEnabled(false);
        } else {
            h.btnLog.setText("LOG MEAL");
            h.btnLog.setBackgroundColor(Color.parseColor("#76B54F"));
            h.btnLog.setEnabled(true);
        }

        h.btnLog.setOnClickListener(v -> {
            m.setLogged(true);
            notifyItemChanged(pos);
            if (listener != null) listener.onMealUpdate();
        });
    }

    @Override public int getItemCount() { return mealList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDesc;
        Button btnLog;
        public ViewHolder(View v) {
            super(v);
            tvTitle = v.findViewById(R.id.tvMealTimeTitle);
            tvDesc = v.findViewById(R.id.tvMealDesc);
            btnLog = v.findViewById(R.id.btnLogMealAction);
        }
    }
}