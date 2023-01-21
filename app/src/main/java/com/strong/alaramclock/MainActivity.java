package com.strong.alaramclock;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.strong.alaramclock.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    ArrayList<getSet> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        list.add(new getSet("10:30"));
        list.add(new getSet("10:30"));
        list.add(new getSet("10:30"));
        list.add(new getSet("10:30"));
        list.add(new getSet("10:30"));
        alarmAdopter adopter = new alarmAdopter(this, list);
        binding.recyclerView.setAdapter(adopter);

        setContentView(binding.getRoot());

        binding.addAlarm.setOnClickListener(v -> {
            startActivity(new Intent(this, alarm.class));
        });
    }
}