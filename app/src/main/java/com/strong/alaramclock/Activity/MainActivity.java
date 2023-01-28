package com.strong.alaramclock.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.strong.alaramclock.Datatabase.Alarm_BED;
import com.strong.alaramclock.Datatabase.DatabaseClient;
import com.strong.alaramclock.alarmAdopter;
import com.strong.alaramclock.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        /*ist.add(new getSet("10:30"));
        list.add(new getSet("10:30"));
        list.add(new getSet("10:30"));
        list.add(new getSet("10:30"));
        list.add(new getSet("10:30"));
        alarmAdopter adopter = new alarmAdopter(this, list);*/

        getTasks();

        setContentView(binding.getRoot());

        binding.addAlarm.setOnClickListener(v -> {
            startActivity(new Intent(this, alarm_set.class));
        });
    }

    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<Alarm_BED>> {

            @Override
            protected List<Alarm_BED> doInBackground(Void... voids) {
                List<Alarm_BED> taskList = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().TimeDao().getAll();
                return taskList;
            }

            @Override
            protected void onPostExecute(List<Alarm_BED> tasks) {
                super.onPostExecute(tasks);
                alarmAdopter adapter = new alarmAdopter(MainActivity.this, tasks);
                binding.recyclerView.setAdapter(adapter);
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }
}