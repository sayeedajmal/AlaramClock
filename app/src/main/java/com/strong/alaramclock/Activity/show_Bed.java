package com.strong.alaramclock.Activity;

import static com.strong.alaramclock.Activity.set_Bed.alarmManager;
import static com.strong.alaramclock.services.play;

import android.app.AlarmManager;
import android.app.KeyguardManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.strong.alaramclock.Datatabase.Alarm_BED;
import com.strong.alaramclock.Datatabase.Client_Bed;
import com.strong.alaramclock.databinding.ActivityMorningShowBinding;
import com.strong.alaramclock.services;

import java.util.List;

public class show_Bed extends AppCompatActivity {
    ActivityMorningShowBinding Bind;
    List<Alarm_BED> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bind = ActivityMorningShowBinding.inflate(getLayoutInflater());

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);


//        SCREEN ON WHILE ALARM ACTIVATED
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
            KeyguardManager manager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);
            manager.requestDismissKeyguard(this, null);
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        Bind.label.setText(getIntent().getStringExtra("Label"));
        String TimeStamp = getIntent().getStringExtra("TimeStamp");
        Alarm_BED alarm_bed = new Alarm_BED();


        Bind.CancelAlarm.setOnClickListener(view -> {
            Intent intent = new Intent(this, services.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(show_Bed.this, 123, intent, PendingIntent.FLAG_IMMUTABLE);
            if (play != null) {
                if (alarmManager == null) {
                    alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                }
                play.stop();
                alarmManager.cancel(pendingIntent);
                DeleteAlarm(alarm_bed, this, TimeStamp);
                Toast.makeText(this, "RECITE THE DUA OF WAKEUP & MORNING DUA", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }

        });
        setContentView(Bind.getRoot());
    }

    private void DeleteAlarm(Alarm_BED alarm, Context context, String TimeStamp) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Client_Bed.getInstance(context).getAppDatabase().TimeDao().delete(alarm);
                return null;
            }
        }
        DeleteTask task = new DeleteTask();
        task.execute();
    }

    private int getList() {
        return list.size();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}