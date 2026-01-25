package com.taksh.petspalace10;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ActivityLogAdapter extends RecyclerView.Adapter<ActivityLogAdapter.ViewHolder> {
    private List<ActivityLogModel> list;
    private int expandedPosition = -1; // Keeps track of which card is open

    public ActivityLogAdapter(List<ActivityLogModel> list) { this.list = list; }

    @NonNull @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup p, int viewType) {
        return new ViewHolder(LayoutInflater.from(p.getContext()).inflate(R.layout.item_activity_log, p, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        ActivityLogModel m = list.get(pos);
        h.title.setText(m.getTitle());
        h.time.setText(m.getTimeSlot());
        h.icon.setImageResource(m.getIconRes());
        h.bg.setBackgroundTintList(ColorStateList.valueOf(m.getIconBgColor()));

        // Check if this item should be expanded
        final boolean isExpanded = pos == expandedPosition;
        h.expandableLayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
        h.itemView.setActivated(isExpanded);

        // Click to expand/collapse
        h.itemView.setOnClickListener(v -> {
            expandedPosition = isExpanded ? -1 : pos;
            notifyDataSetChanged(); // Refreshes the list to show/hide sections
        });

        // Timer Logic
        h.btnStart.setOnClickListener(v -> {
            h.btnStart.setVisibility(View.GONE);
            String digits = m.getDuration().replaceAll("[^0-9]", "");
            long mins = digits.isEmpty() ? 1 : Long.parseLong(digits);

            new CountDownTimer(mins * 60000, 1000) {
                public void onTick(long millis) {
                    h.tvCountdown.setText(String.format("%02d:%02d", (millis/60000), (millis%60000)/1000));
                }
                public void onFinish() {
                    m.setCompleted(true);
                    h.tvCountdown.setText("Goal Reached!");
                    updateDotUI(h, true);
                }
            }.start();
        });

        updateDotUI(h, m.isCompleted());
    }

    private void updateDotUI(ViewHolder h, boolean done) {
        int color = done ? Color.parseColor("#2196F3") : Color.parseColor("#D1D1D1");
        h.dot.setBackgroundTintList(ColorStateList.valueOf(color));
        h.line.setBackgroundColor(done ? Color.parseColor("#2196F3") : Color.parseColor("#EEEEEE"));
    }

    @Override public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, time, tvCountdown;
        ImageView icon;
        View bg, dot, line;
        LinearLayout expandableLayout;
        Button btnStart;

        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.tvActivityTitle);
            time = v.findViewById(R.id.tvStartTime);
            icon = v.findViewById(R.id.imgActivityIcon);
            bg = v.findViewById(R.id.iconBg);
            dot = v.findViewById(R.id.timelineDot);
            line = v.findViewById(R.id.timelineLine);
            expandableLayout = v.findViewById(R.id.expandableLayout);
            tvCountdown = v.findViewById(R.id.tvCountdown);
            btnStart = v.findViewById(R.id.btnStartTimer);
        }
    }
}