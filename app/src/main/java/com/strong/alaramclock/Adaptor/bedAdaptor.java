package com.strong.alaramclock.Adaptor;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.strong.alaramclock.Datatabase.Alarm_BED;
import com.strong.alaramclock.Datatabase.Client_Bed;
import com.strong.alaramclock.R;
import com.strong.alaramclock.services;

import java.util.List;

public class bedAdaptor extends RecyclerView.Adapter<bedAdaptor.ViewHolder> {
    Context context;
    List<Alarm_BED> list;

    public bedAdaptor(Context context, List<Alarm_BED> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public bedAdaptor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.sample_alarm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull bedAdaptor.ViewHolder holder, int position) {
        Alarm_BED data = list.get(position);
        holder.day.setText(data.getDay());
        holder.time.setText(data.getTime());
        holder.amPm.setText(data.getAmPm());
        holder.description.setText(data.getLabel());
        if (data.getDay().equals("Daily"))
            holder.Icon.setImageResource(R.drawable.round_time);

        if (data.getAmPm().equals("PM")) {
            holder.timeImage.setImageResource(R.drawable.ic_sun);
        } else holder.timeImage.setImageResource(R.drawable.ic_light);

        holder.itemView.setOnLongClickListener(view -> {
            holder.delete.setVisibility(View.VISIBLE);
            return true;
        });

        holder.itemView.setOnClickListener((view) -> {
            holder.delete.setVisibility(View.GONE);
        });

        Alarm_BED deleteItem = list.get(position);
        holder.delete.setOnClickListener(v -> deleteTask(deleteItem, position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void deleteTask(final Alarm_BED alarm, int position) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Client_Bed.getInstance(context).getAppDatabase().TimeDao().delete(alarm);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                list.remove(position);
                notifyItemRemoved(position);
                Intent intent = new Intent(context, services.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 123, intent, PendingIntent.FLAG_IMMUTABLE);
                AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                alarmManager.cancel(pendingIntent);

            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView day, time, amPm, description;
        ImageView timeImage, Icon;
        ImageButton delete;
        SwitchCompat onOff;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            delete = itemView.findViewById(R.id.delete);
            day = itemView.findViewById(R.id.day);
            time = itemView.findViewById(R.id.time);
            amPm = itemView.findViewById(R.id.amPm);
            description = itemView.findViewById(R.id.description);
            timeImage = itemView.findViewById(R.id.timeImage);
            onOff = itemView.findViewById(R.id.onOff);
            Icon = itemView.findViewById(R.id.Icon);
        }
    }
}
