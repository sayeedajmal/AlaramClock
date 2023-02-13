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
import com.strong.alaramclock.Datatabase.Client_Bed;
import com.strong.alaramclock.R;
import com.strong.alaramclock.databinding.ActivityBedAlarmBinding;
import com.strong.alaramclock.services;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import nl.joery.timerangepicker.TimeRangePicker;

public class set_Bed extends AppCompatActivity {
    static int sleepAtHour, sleepAtMinute, wakeAtHour, wakeAtMinute;
    static AlarmManager alarmManager;
    final LocalDateTime date = LocalDateTime.now();
    BottomSheetDialog bottomSheetDialog;
    ActivityBedAlarmBinding AlarmBind;
    int totalDuration;
    Calendar calendar = Calendar.getInstance();

    public static String Hour_12(int hour) {
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

    static String AMorPM(int hour) {
        if (hour >= 12) {
            return "PM";
        }
        return "AM";
    }

    static String getMinute(int minute) {
        if (minute == 0) {
            return "00";
        }
        if (minute <= 9) {
            return "0" + minute;
        }
        return String.valueOf(minute);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlarmBind = ActivityBedAlarmBinding.inflate(getLayoutInflater());
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
                AlarmBind.sleepAtHour.setText(Hour_12(time.getHour()) + ":" + getMinute(time.getMinute()) + " " + AMorPM(time.getHour()));
                AlarmBind.bedTime.setText(Hour_12(time.getHour()) + ":" + getMinute(time.getMinute()) + " " + AMorPM(time.getHour()));

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onEndTimeChange(@NonNull TimeRangePicker.Time time) {
                wakeAtHour = time.getHour();
                wakeAtMinute = time.getMinute();
                AlarmBind.wakAtHour.setText(Hour_12(time.getHour()) + ":" + getMinute(time.getMinute()) + " " + AMorPM(time.getHour()));
                AlarmBind.wakeTime.setText(Hour_12(time.getHour()) + ":" + getMinute(time.getMinute()) + " " + AMorPM(time.getHour()));
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

        AlarmBind.SetAlarm.setOnClickListener(v -> setTimeCalender());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void setTimeCalender() {
        calendar.set(Calendar.HOUR_OF_DAY, wakeAtHour);
        calendar.set(Calendar.MINUTE, wakeAtMinute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Save_Alarm(calendar);
    }

    private void Save_Alarm(Calendar calendar) {
        String Label = Objects.requireNonNull(AlarmBind.AlarmLabel.getText()).toString();
        String Day = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.MONTH, Locale.getDefault());
        String Time = Hour_12(wakeAtHour) + ":" + getMinute(wakeAtMinute);
        String AMorPm = AMorPM(wakeAtHour);

        long CurrentTime = System.currentTimeMillis();
        if (CurrentTime >= calendar.getTimeInMillis()) {
            Toast.makeText(this, "Given Time is Passed. Give The Next Time.", Toast.LENGTH_SHORT).show();
            return;
        }

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

                //adding to database
                Client_Bed.getInstance(getApplicationContext()).getAppDatabase().TimeDao().insert(alarm);

                setAlarm(calendar);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
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
       }
}