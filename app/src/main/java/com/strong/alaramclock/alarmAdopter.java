package com.strong.alaramclock;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.strong.alaramclock.Activity.alarm_show;
import com.strong.alaramclock.Datatabase.Alarm_BED;

import java.util.List;

public class alarmAdopter extends RecyclerView.Adapter<alarmAdopter.ViewHolder> {
    Context context;
    List<Alarm_BED> list;

    public alarmAdopter(Context context, List<Alarm_BED> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public alarmAdopter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_alarm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull alarmAdopter.ViewHolder holder, int position) {
        Alarm_BED data = list.get(position);
        holder.day.setText(data.getDay());
        holder.time.setText(data.getTime());
        holder.amPm.setText(data.getAmPm());
        holder.description.setText(data.getLabel());
        if (data.isOnOff()) {
            holder.onOff.setText("ON");
        } else {
            holder.onOff.setText("OFF");
        }

        holder.itemView.setOnClickListener(view -> context.startActivity(new Intent(context, alarm_show.class)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView day, time, amPm, description;
        SwitchCompat onOff;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            day = itemView.findViewById(R.id.day);
            time = itemView.findViewById(R.id.time);
            amPm = itemView.findViewById(R.id.amPm);
            description = itemView.findViewById(R.id.description);
            onOff = itemView.findViewById(R.id.onOff);
        }
    }
}
