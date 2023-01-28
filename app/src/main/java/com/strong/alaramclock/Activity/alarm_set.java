package com.strong.alaramclock.Activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.strong.alaramclock.Datatabase.Alarm_BED;
import com.strong.alaramclock.Datatabase.DatabaseClient;
import com.strong.alaramclock.R;
import com.strong.alaramclock.databinding.ActivityAlarmBinding;
import com.strong.alaramclock.services;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;

import nl.joery.timerangepicker.TimeRangePicker;

public class alarm_set extends AppCompatActivity {
    static int sleepAtHour, sleepAtMinute, wakeAtHour, wakeAtMinute;
    BottomSheetDialog bottomSheetDialog;
    static AlarmManager alarmManager;
    ActivityAlarmBinding AlarmBind;
    int totalDuration;
    final LocalDateTime date = LocalDateTime.now();
    Calendar calendar = Calendar.getInstance();

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
        wakeAtHour = 6;
        wakeAtMinute = 15;

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

        AlarmBind.Calender.setOnClickListener(view -> {
            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.fragment_bottom_dialog);
            DatePicker datePicker = bottomSheetDialog.findViewById(R.id.DatePicker);
            assert datePicker != null;
            datePicker.setOnDateChangedListener((datePicker1, i, i1, i2) -> {
                calendar.set(i, i1, i2);
                int month = i1 + 1;
                AlarmBind.day.setText(i2 + " - " + month + " - " + i);
                bottomSheetDialog.cancel();
            });
            bottomSheetDialog.show();
            bottomSheetDialog.setCanceledOnTouchOutside(true);
            bottomSheetDialog.setOnDismissListener(dialogInterface -> bottomSheetDialog.hide());
        });

        AlarmBind.backButton.setOnClickListener(v -> onBackPressed());

        AlarmBind.SetAlarm.setOnClickListener(v -> AddingAlarm());
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

    private void AddingAlarm() {
        calendar.set(Calendar.HOUR_OF_DAY, wakeAtHour);
        calendar.set(Calendar.MINUTE, wakeAtMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        saveTask(calendar);
    }


    private void saveTask(Calendar calendar) {
        String Label = AlarmBind.AlarmLabel.getText().toString();
        String Day = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.MONTH, Locale.getDefault());
        String Time = Hour_12(wakeAtHour) + ":" + wakeAtMinute;
        String AMorPm = AMorPM(wakeAtHour);
        if (Label.isEmpty()) {
            AlarmBind.AlarmLabel.setError("Alarm Name Required");
            AlarmBind.AlarmLabel.requestFocus();
            return;
        }
        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a task
                Alarm_BED alarm = new Alarm_BED();
                alarm.setTimeInMilliSec(calendar.getTimeInMillis());
                alarm.setDay(Day);
                alarm.setTime(Time);
                alarm.setAmPm(AMorPm);
                alarm.setLabel(Label);
                alarm.setOnOff(true);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().TimeDao().insert(alarm);

                setAlarm(calendar);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "SLEEP WELL. YOU HAVE ONLY " + totalDuration + " MINUTES.", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }

    private void setAlarm(Calendar calendar) {
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, services.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 123, intent, PendingIntent.FLAG_IMMUTABLE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES, pendingIntent);
        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 5000, 2000, pendingIntent);
    }
}