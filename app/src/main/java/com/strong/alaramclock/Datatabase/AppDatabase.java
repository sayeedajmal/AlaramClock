package com.strong.alaramclock.Datatabase;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Alarm_BED.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract roomDAO TimeDao();
}