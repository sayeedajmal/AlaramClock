package com.strong.alaramclock;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.strong.alaramclock.databinding.ActivityAlarmBinding;

import nl.joery.timerangepicker.TimeRangePicker;

public class alarm extends AppCompatActivity {
    static int sleepAtHour, sleepAtMinute, wakeAtHour, wakeAtMinute;
    BottomSheetDialog bottomSheetDialog;
    ActivityAlarmBinding AlarmBind;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlarmBind = ActivityAlarmBinding.inflate(getLayoutInflater());
        setContentView(AlarmBind.getRoot());

        AlarmBind.sleepAtHour.setText("01:15 AM");
        sleepAtHour = 1;
        sleepAtMinute = 15;
        AlarmBind.wakAtHour.setText("02:00 AM");
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
            }
        });

        AlarmBind.Calender.setOnClickListener(view -> {
            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.fragment_bottom_dialog);
            DatePicker datePicker = bottomSheetDialog.findViewById(R.id.DatePicker);
            assert datePicker != null;
            datePicker.setOnDateChangedListener((datePicker1, i, i1, i2) -> {
                AlarmBind.selectedDate.setText(datePicker1.getDayOfMonth() + " / " + (i1 + 1) + " / " + datePicker1.getYear());
                bottomSheetDialog.cancel();
            });
            bottomSheetDialog.show();
            bottomSheetDialog.setCanceledOnTouchOutside(true);
            bottomSheetDialog.setOnDismissListener(dialogInterface -> bottomSheetDialog.hide());
        });

        AlarmBind.backButton.setOnClickListener(v -> onBackPressed());
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
}