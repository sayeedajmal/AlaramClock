package com.strong.alaramclock.Activity;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.Settings;
import android.view.View;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import com.strong.alaramclock.databinding.ActivityCountTimerBinding;

public class CountTimer extends AppCompatActivity {
    ActivityCountTimerBinding Bind;
    CountDownTimer Timer;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bind = ActivityCountTimerBinding.inflate(getLayoutInflater());
        setContentView(Bind.getRoot());

        Bind.rangeSlider.setMax(120);
        Bind.progressBar.setMax(120);
        Bind.progressBar.setProgress(0);
        Bind.TimeLeft.setText("Scroll To Start");
        Bind.rangeSlider.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                Bind.progressBar.setProgress(seekBar.getProgress(), true);
                Bind.TimeLeft.setText(String.valueOf(seekBar.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Bind.progressBar.setMax(seekBar.getProgress());
                Bind.rangeSlider.setVisibility(View.INVISIBLE);
                Bind.Cancel.setVisibility(View.VISIBLE);
                Timer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                    @Override
                    public void onTick(long l) {
                        Bind.textview.setVisibility(View.VISIBLE);
                        Bind.textview.setText("Left");
                        Bind.TimeLeft.setText(String.valueOf(l / 1000));
                        Bind.progressBar.setProgress((int) l / 1000, true);
                        if ((int) l / 1000 == 0) {
                            Bind.TimeLeft.setText("Time Up");
                            Bind.textview.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onFinish() {
                        Bind.TimeLeft.setText("Time Up");
                        Alert(CountTimer.this);
                        Bind.textview.setVisibility(View.INVISIBLE);
                        Bind.Cancel.setVisibility(View.INVISIBLE);
                        Bind.STOP.setVisibility(View.VISIBLE);
                    }
                }.start();
            }
        });

        Bind.STOP.setOnClickListener(view -> {
            Bind.rangeSlider.setVisibility(View.VISIBLE);
            Bind.STOP.setVisibility(View.INVISIBLE);
            Bind.Cancel.setVisibility(View.INVISIBLE);
            Bind.progressBar.setProgress(0);
            Bind.textview.setVisibility(View.INVISIBLE);
            Alert(CountTimer.this);
            Bind.TimeLeft.setText("Scroll To Start");
        });

        Bind.Cancel.setOnClickListener(v -> {
            Bind.rangeSlider.setVisibility(View.VISIBLE);
            Bind.STOP.setVisibility(View.INVISIBLE);
            Bind.textview.setVisibility(View.INVISIBLE);
            Bind.Cancel.setVisibility(View.INVISIBLE);
            Bind.progressBar.setProgress(0);
            Timer.cancel();
            Bind.TimeLeft.setText("Scroll To Start");
        });
        Bind.backButton.setOnClickListener(v -> onBackPressed());
    }


    @Override
    protected void onPause() {
        super.onPause();
        mediaPlayer.stop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
    }

    private void Alert(Context context) {
        mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_NOTIFICATION_URI);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);
    }

}