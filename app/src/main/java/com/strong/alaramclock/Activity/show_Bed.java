package com.strong.alaramclock.Activity;

import static com.strong.alaramclock.Activity.set_Bed.alarmManager;
import static com.strong.alaramclock.services.play;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.strong.alaramclock.databinding.ActivityMorningShowBinding;
import com.strong.alaramclock.services;

public class show_Bed extends AppCompatActivity {
    ActivityMorningShowBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMorningShowBinding.inflate(getLayoutInflater());

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


//        SCREEN ON WHILE ALARM ACTIVATED
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
            KeyguardManager manager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            manager.requestDismissKeyguard(this, null);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        binding.CancelAlarm.setOnClickListener(view -> {
            Intent intent = new Intent(this, services.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(show_Bed.this, 123, intent, PendingIntent.FLAG_IMMUTABLE);
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
        setContentView(binding.getRoot());
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}