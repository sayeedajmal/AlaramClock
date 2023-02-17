package com.strong.alaramclock;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.Settings;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.strong.alaramclock.Activity.show_Daily;
import com.strong.alaramclock.Datatabase.Alarm_BED;

import java.util.List;

public class services extends BroadcastReceiver {
    public static MediaPlayer play;
    List<Alarm_BED> list;

    @Override
    public void onReceive(Context context, Intent i) {
        Intent intent = new Intent(context, show_Daily.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 123, intent, PendingIntent.FLAG_IMMUTABLE);

        Bitmap bitmap = BitmapFactory.decodeResource(Resources.getSystem(), R.drawable.ic_light);
        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (context, "BED_TIME")
                .setLargeIcon(bitmap)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                .setSmallIcon(R.drawable.ic_alarm)
                .setColorized(true)
                .setBadgeIconType(NotificationCompat.BADGE_ICON_LARGE)
                .setVibrate(new long[]{1000, 1000, 1000, 1000})
                .setContentTitle("HEY! YOUR TIME IS UP..")
                .setContentText("I Hope You Slept Well..")
                .setAutoCancel(true).setDefaults(NotificationCompat.GROUP_ALERT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setFullScreenIntent(pendingIntent, true);

        builder.setOngoing(true);
        NotificationChannel channel = new NotificationChannel("BED_TIME", "BED TIME", NotificationManager.IMPORTANCE_HIGH);
        channel.enableLights(true);
        channel.enableVibration(true);
        channel.setLockscreenVisibility(1);
        NotificationManager manager = context.getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);

        play(context);
        managerCompat.notify(123, builder.build());
    }

    private void play(Context context) {
        play = MediaPlayer.create(context, Settings.System.DEFAULT_ALARM_ALERT_URI);
        play.setLooping(true);
        play.start();
        new Handler().postDelayed(() -> play.stop(), 2 * 60 * 1000);
    }
}
