package com.strong.alaramclock;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.strong.alaramclock.databinding.ActivityAlarmBinding;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Calendar;

import nl.joery.timerangepicker.TimeRangePicker;

public class alarm extends AppCompatActivity {
    static int sleepAtHour, sleepAtMinute, wakeAtHour, wakeAtMinute;
    BottomSheetDialog bottomSheetDialog;
    static AlarmManager alarmManager;
    ActivityAlarmBinding AlarmBind;
    int totalDuration;
    final LocalDateTime date = LocalDateTime.now();
    Calendar calendar;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlarmBind = ActivityAlarmBinding.inflate(getLayoutInflater());
        setContentView(AlarmBind.getRoot());


        DayOfWeek getDay = date.getDayOfWeek();
        AlarmBind.day.setText(String.valueOf(getDay));
        sleepAtHour = date.getHour();
        sleepAtMinute = date.getMinute();
        AlarmBind.TimePicker.setEndTime(new TimeRangePicker.Time(sleepAtHour, sleepAtMinute));
        AlarmBind.sleepAtHour.setText(sleepAtHour + ":" + sleepAtMinute + " " + AMorPM(sleepAtHour));
        AlarmBind.bedTime.setText(sleepAtHour + ":" + sleepAtMinute + " " + AMorPM(sleepAtHour));
        AlarmBind.wakAtHour.setText("06:15 AM");
        wakeAtHour = 2;
        wakeAtMinute = 0;

        AlarmBind.TimePicker.setOnTimeChangeListener(new TimeRangePicker.OnTimeChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onStartTimeChange(@NonNull TimeRangePicker.Time time) {
                sleepAtHour = time.getHour();
                sleepAtMinute = time.getMinute();
                AlarmBind.sleepAtHour.setText(Hour_12(time.getHour()) + ":" + Minute(time.getMinute()) + " " + AMorPM(time.getHour()));
                AlarmBind.bedTime.setText(Hour_12(time.getHour()) + ":" + Minute(time.getMinute()) + " " + AMorPM(time.getHour()));

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onEndTimeChange(@NonNull TimeRangePicker.Time time) {
                wakeAtHour = time.getHour();
                wakeAtMinute = time.getMinute();
                AlarmBind.wakAtHour.setText(Hour_12(time.getHour()) + ":" + Minute(time.getMinute()) + " " + AMorPM(time.getHour()));
                AlarmBind.wakeTime.setText(Hour_12(time.getHour()) + ":" + Minute(time.getMinute()) + " " + AMorPM(time.getHour()));
            }

            @Override
            public void onDurationChange(@NonNull TimeRangePicker.TimeDuration timeDuration) {
                totalDuration = timeDuration.getDurationMinutes();
            }
        });

        /*AlarmBind.Calender.setOnClickListener(view -> {
            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.fragment_bottom_dialog);
            DatePicker datePicker = bottomSheetDialog.findViewById(R.id.DatePicker);
            assert datePicker != null;
            datePicker.setOnDateChangedListener((datePicker1, i, i1, i2) -> {
                Calendar cal = Calendar.getInstance();
                cal.set(i, i1, i2);
                AlarmBind.day.setText(String.valueOf(cal.get(Calendar.DAY_OF_WEEK)));
                bottomSheetDialog.cancel();
            });
            bottomSheetDialog.show();
            bottomSheetDialog.setCanceledOnTouchOutside(true);
            bottomSheetDialog.setOnDismissListener(dialogInterface -> bottomSheetDialog.hide());
        });*/

        AlarmBind.backButton.setOnClickListener(v -> onBackPressed());

        AlarmBind.SetAlarm.setOnClickListener(v -> setAlarm());
    }

    private String Hour_12(int hour) {
        if (hour % 12 == 0) {
            return "12";
        }
        if (hour < 10) {
            return "0" + hour;
        }
        if (hour >= 12) {
            return String.valueOf(hour % 12);
        }
        return String.valueOf(hour);
    }

    private String AMorPM(int hour) {
        if (hour >= 12) {
            return "PM";
        }
        return "AM";
    }

    private String Minute(int minute) {
        if (minute == 0) {
            return "00";
        }
        if (minute < 9) {
            return "0" + minute;
        }
        return String.valueOf(minute);
    }

    private void setAlarm() {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, services.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 123, intent, PendingIntent.FLAG_IMMUTABLE);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, wakeAtHour);
        calendar.set(Calendar.MINUTE, wakeAtMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, 2000, pendingIntent);
        Toast.makeText(this, "SLEEP WELL. YOU HAVE ONLY " + totalDuration + " MINUTES.", Toast.LENGTH_LONG).show();
        onBackPressed();
    }
}