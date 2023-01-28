package com.strong.alaramclock.Activity;

import static com.strong.alaramclock.Activity.alarm_set.alarmManager;
import static com.strong.alaramclock.services.play;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.strong.alaramclock.databinding.ActivityAlarmShowBinding;
import com.strong.alaramclock.services;

public class alarm_show extends AppCompatActivity {
    ActivityAlarmShowBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAlarmShowBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.CancelAlarm.setOnClickListener(view -> {
            Intent intent = new Intent(this, services.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(alarm_show.this, 123, intent, PendingIntent.FLAG_IMMUTABLE);
            if (play != null) {
                if (alarmManager == null) {
                    alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                }
                play.stop();
                alarmManager.cancel(pendingIntent);
                Toast.makeText(this, "RECITE THE DUA OF WAKEUP & MORNING DUA", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }

        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}