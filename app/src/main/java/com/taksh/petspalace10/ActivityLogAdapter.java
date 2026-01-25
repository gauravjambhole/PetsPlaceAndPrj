package com.taksh.petspalace10;

import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ActivityLogAdapter extends RecyclerView.Adapter<ActivityLogAdapter.ViewHolder> {
    private List<ActivityLogModel> list;

    public ActivityLogAdapter(List<ActivityLogModel> list) { this.list = list; }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup p, int viewType) {
        return new ViewHolder(LayoutInflater.from(p.getContext()).inflate(R.layout.item_activity_log, p, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int pos) {
        ActivityLogModel m = list.get(pos);
        h.title.setText(m.getTitle());
        h.time.setText(m.getTimeSlot());
        h.duration.setText(m.getDuration());
        h.icon.setImageResource(m.getIconRes());
        h.bg.setBackgroundTintList(ColorStateList.valueOf(m.getIconBgColor()));
    }

    @Override
    public int getItemCount() { return list.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, time, duration;
        ImageView icon;
        View bg;
        public ViewHolder(View v) {
            super(v);
            title = v.findViewById(R.id.tvActivityTitle);
            time = v.findViewById(R.id.tvStartTime);
            duration = v.findViewById(R.id.tvDuration);
            icon = v.findViewById(R.id.imgActivityIcon);
            bg = v.findViewById(R.id.iconBg);
        }
    }
}