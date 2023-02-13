package com.strong.alaramclock.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.strong.alaramclock.Adaptor.bedAdaptor;
import com.strong.alaramclock.Datatabase.Alarm_BED;
import com.strong.alaramclock.Datatabase.Client_Bed;
import com.strong.alaramclock.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    bedAdaptor adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        getBedAlarm();

        setContentView(binding.getRoot());

        binding.addAlarm.setOnClickListener(v -> {
            startActivity(new Intent(this, set_Bed.class));
            finish();
        });

        binding.dailyAlarm.setOnClickListener(v -> startActivity(new Intent(this, set_Daily.class)));
    }

    public void getBedAlarm() {
        class getAlarm extends AsyncTask<Void, Void, List<Alarm_BED>> {

            @Override
            protected List<Alarm_BED> doInBackground(Void... voids) {
                return Client_Bed.getInstance(MainActivity.this).getAppDatabase().TimeDao().getAll();
            }

            @Override
            protected void onPostExecute(List<Alarm_BED> tasks) {
                super.onPostExecute(tasks);
                adapter = new bedAdaptor(MainActivity.this, tasks);
                binding.recyclerView.setAdapter(adapter);
            }
        }

        getAlarm gt = new getAlarm();
        gt.execute();
    }

//    public void getDailyAlarm() {
//        class getAlarm extends AsyncTask<Void, Void, List<Alarm_Daily>> {
//
//            @Override
//            protected List<Alarm_Daily> doInBackground(Void... voids) {
//                return Client_Daily.getInstance(MainActivity.this).getDatabase_daily().TimeDao().getAll();
//            }
//
//            @Override
//            protected void onPostExecute(List<Alarm_Daily> tasks) {
//                super.onPostExecute(tasks);
//                adapter = new bedAdaptor(MainActivity.this, tasks, "null");
//                binding.recyclerView.setAdapter(adapter);
//            }
//        }
//
//        getAlarm gt = new getAlarm();
//        gt.execute();
//    }

}