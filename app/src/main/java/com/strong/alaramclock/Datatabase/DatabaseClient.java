package com.strong.alaramclock.Datatabase;

import android.content.Context;

import androidx.room.Room;

public class DatabaseClient {

    Context context;
    static DatabaseClient mInstance;

    //our app database object
    private AppDatabase appDatabase;

    private DatabaseClient(Context context) {
        this.context = context;

        //creating the app database with Room database builder
        //BED_TIME is the name of the database
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "BED_TIME").build();
    }

    public static synchronized DatabaseClient getInstance(Context mCtx) {
        if (mInstance == null) {
            mInstance = new DatabaseClient(mCtx);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
