package com.strong.alaramclock.Activity;

import static com.strong.alaramclock.Activity.set_Bed.AMorPM;
import static com.strong.alaramclock.Activity.set_Bed.Hour_12;
import static com.strong.alaramclock.Activity.set_Bed.getMinute;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.strong.alaramclock.Datatabase.Alarm_BED;
import com.strong.alaramclock.Datatabase.Client_Bed;
import com.strong.alaramclock.databinding.ActivityDailyAlarmSetBinding;
import com.strong.alaramclock.services;

import java.util.Calendar;

import nl.joery.timerangepicker.TimeRangePicker;

public class set_Daily extends AppCompatActivity {

    ActivityDailyAlarmSetBinding Bind;
    int Hour, Minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bind = ActivityDailyAlarmSetBinding.inflate(getLayoutInflater());

        Bind.backButton.setOnClickListener(v -> onBackPressed());

        setContentView(Bind.getRoot());
        setTime();
        Bind.SetAlarm.setOnClickListener(view -> {
            SetDailyAlarm();
            Snackbar.make(Bind.SetAlarm, "Alarm Added", Snackbar.LENGTH_LONG).show();
            finish();
            startActivity(new Intent(this, MainActivity.class));
        });
    }

    private void setTime() {
        Bind.DailyTime.setOnTimeChangeListener(new TimeRangePicker.OnTimeChangeListener() {
            @Override
            public void onStartTimeChange(@NonNull TimeRangePicker.Time time) {

            }

            @Override
            public void onEndTimeChange(@NonNull TimeRangePicker.Time time) {
                Hour = time.getHour();
                Minute = time.getMinute();
                Bind.dailyTime.setText(Hour_12(time.getHour()) + ":" + getMinute(time.getMinute()) + " " + AMorPM(time.getHour()));
            }

            @Override
            public void onDurationChange(@NonNull TimeRangePicker.TimeDuration timeDuration) {

            }
        });
    }

    private void SetDailyAlarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, services.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 123, intent, PendingIntent.FLAG_IMMUTABLE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Hour);
        calendar.set(Calendar.MINUTE, Minute);
        calendar.set(Calendar.SECOND, 0);

        SaveAlarm(calendar);
        manager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
    }

    private void SaveAlarm(Calendar calender) {
        String label = Bind.dailyLabel.getText().toString();
        String Day = "Daily";
        String Time = Hour_12(Hour) + ":" + getMinute(Minute);
        String Am0rPm = AMorPM(Hour);
        if (label.isEmpty()) {
            Bind.dailyLabel.setError("Alarm Name Required");
            return;
        }

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                Alarm_BED alarm_daily = new Alarm_BED();
                alarm_daily.setTimeInMilliSec(calender.getTimeInMillis());
                alarm_daily.setDay(Day);
                alarm_daily.setTime(Time);
                alarm_daily.setAmPm(Am0rPm);
                alarm_daily.setLabel(label);
                alarm_daily.setFinished(false);

                Client_Bed.getInstance(getApplicationContext()).getAppDatabase().TimeDao().insert(alarm_daily);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                Toast.makeText(set_Daily.this, "Alarm Added", Toast.LENGTH_SHORT).show();
            }
        }
        SaveTask task = new SaveTask();
        task.execute();
    }
}