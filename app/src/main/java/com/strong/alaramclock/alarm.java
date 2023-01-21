package com.strong.alaramclock;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.strong.alaramclock.databinding.ActivityAlarmBinding;

import nl.joery.timerangepicker.TimeRangePicker;

public class alarm extends AppCompatActivity {
    static String sleepAtHour, sleepAtMinute, wakeAtHour, wakeAtMinute;
    BottomSheetDialog bottomSheetDialog;
    ActivityAlarmBinding AlarmBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AlarmBind = ActivityAlarmBinding.inflate(getLayoutInflater());
        setContentView(AlarmBind.getRoot());

        AlarmBind.sleepAtHour.setText("1:15");
        sleepAtHour = "1";
        sleepAtMinute = "15";
        AlarmBind.wakAtHour.setText("2:00");
        wakeAtHour = "2";
        wakeAtMinute = "0";
        AlarmBind.TimePicker.setOnTimeChangeListener(new TimeRangePicker.OnTimeChangeListener() {
            @Override
            public void onStartTimeChange(@NonNull TimeRangePicker.Time time) {
                sleepAtHour = String.valueOf(time.getHour());
                sleepAtMinute = String.valueOf(time.getMinute());
                AlarmBind.sleepAtHour.setText(time.getHour() + ":" + time.getMinute());
                AlarmBind.bedTime.setText(time.getHour() + ":" + time.getMinute());
            }

            @Override
            public void onEndTimeChange(@NonNull TimeRangePicker.Time time) {
                wakeAtHour = String.valueOf(time.getHour());
                wakeAtMinute = String.valueOf(time.getMinute());
                AlarmBind.wakAtHour.setText(time.getHour() + ":" + time.getMinute());
                AlarmBind.wakeTime.setText(time.getHour() + ":" + time.getMinute());
            }

            @Override
            public void onDurationChange(@NonNull TimeRangePicker.TimeDuration timeDuration) {
                AlarmBind.totalDuration.setText("Alarmed after " + timeDuration.getHour() + " Hour & " + timeDuration.getMinute() + " Minutes");
            }
        });

        AlarmBind.Calender.setOnClickListener(view -> {
            bottomSheetDialog = new BottomSheetDialog(this);
            bottomSheetDialog.setContentView(R.layout.fragment_bottom_dialog);
            bottomSheetDialog.show();
            bottomSheetDialog.setCanceledOnTouchOutside(true);
            bottomSheetDialog.setOnDismissListener(dialogInterface -> bottomSheetDialog.hide());
        });

        AlarmBind.backButton.setOnClickListener(v -> onBackPressed());
    }
}