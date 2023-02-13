package com.strong.alaramclock.Datatabase;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Alarm_BED.class}, version = 1)
public abstract class Database_Bed extends RoomDatabase {
    public abstract DAO_Bed TimeDao();
}