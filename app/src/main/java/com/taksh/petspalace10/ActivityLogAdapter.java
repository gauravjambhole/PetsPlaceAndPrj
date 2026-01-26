package com.taksh.petspalace10;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ActivityLogAdapter extends RecyclerView.Adapter<ActivityLogAdapter.ViewHolder> {
    private List<ActivityLogModel> list;
    private int expandedPosition = -1;
    private OnProgressListener listener;
    private ActivityTimerViewModel timerViewModel;

    public interface OnProgressListener { void onProgressUpdate(); }

    public ActivityLogAdapter(List<ActivityLogModel> list, OnProgressListener listener, ActivityTimerViewModel viewModel) {
        this.list = list;
        this.listener = listener;
        this.timerViewModel = viewModel;
    }

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

        h.expandableLayout.setVisibility(pos == expandedPosition && !m.isCompleted() ? View.VISIBLE : View.GONE);
        h.statusCheck.setVisibility(m.isCompleted() ? View.VISIBLE : View.GONE);

        // Observe the timer from ViewModel
        timerViewModel.getTimerValue(m.getTitle()).observe((LifecycleOwner) h.itemView.getContext(), millis -> {
            if (millis > 0) {
                h.tvCountdown.setText(String.format("%02d:%02d", (millis / 60000), (millis % 60000) / 1000));
                h.btnStart.setVisibility(View.GONE);
            } else if (millis == -1L) {
                completeActivity(m);
            }
        });

        h.itemView.setOnClickListener(v -> {
            if (!m.isCompleted()) {
                expandedPosition = (expandedPosition == pos) ? -1 : pos;
                notifyDataSetChanged();
            }
        });

        h.btnStart.setOnClickListener(v -> {
            long mins = Long.parseLong(m.getDuration().replaceAll("[^0-9]", ""));
            timerViewModel.startTimer(m.getTitle(), mins);
            scheduleAlarm(v.getContext(), m.getTitle(), mins);
            notifyItemChanged(pos);
        });

        h.btnDone.setOnClickListener(v -> completeActivity(m));
        updateDotUI(h, m.isCompleted());
    }

    private void scheduleAlarm(Context context, String title, long minutes) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ActivityAlarmReceiver.class);
        intent.putExtra("title", title);
        PendingIntent pi = PendingIntent.getBroadcast(context, title.hashCode(), intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        long trigger = System.currentTimeMillis() + (minutes * 60000);
        if (alarmManager != null) alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, trigger, pi);
    }

    private void completeActivity(ActivityLogModel m) {
        if (!m.isCompleted()) {
            m.setCompleted(true);
            notifyDataSetChanged();
            if (listener != null) listener.onProgressUpdate();
        }
    }

    private void updateDotUI(ViewHolder h, boolean done) {
        int color = done ? Color.parseColor("#2196F3") : Color.parseColor("#D1D1D1");
        h.dot.setBackgroundTintList(ColorStateList.valueOf(color));
        h.line.setBackgroundColor(done ? Color.parseColor("#2196F3") : Color.parseColor("#EEEEEE"));
    }

    @Override public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, time, tvCountdown;
        ImageView icon, statusCheck;
        View bg, dot, line, expandableLayout;
        Button btnStart, btnDone;
        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.tvActivityTitle);
            time = v.findViewById(R.id.tvStartTime);
            tvCountdown = v.findViewById(R.id.tvCountdown);
            icon = v.findViewById(R.id.imgActivityIcon);
            statusCheck = v.findViewById(R.id.imgStatusCheck);
            bg = v.findViewById(R.id.iconBg);
            dot = v.findViewById(R.id.timelineDot);
            line = v.findViewById(R.id.timelineLine);
            expandableLayout = v.findViewById(R.id.expandableLayout);
            btnStart = v.findViewById(R.id.btnStartTimer);
            btnDone = v.findViewById(R.id.btnMarkDone);
        }
    }
}