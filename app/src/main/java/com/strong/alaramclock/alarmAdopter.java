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

import java.util.ArrayList;

public class alarmAdopter extends RecyclerView.Adapter<alarmAdopter.ViewHolder> {
    Context context;
    ArrayList<getSet> list;

    public alarmAdopter(Context context, ArrayList<getSet> list) {
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
        getSet data = list.get(position);
        holder.time.setText(data.getTime());

        holder.onOff.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                holder.onOff.setText("ON");
            } else {
                holder.onOff.setText("OFF");
            }
        });
        holder.itemView.setOnClickListener(view -> context.startActivity(new Intent(context, FullscreenActivity.class)));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        SwitchCompat onOff;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.time);
            onOff = itemView.findViewById(R.id.onOff);
        }
    }
}
