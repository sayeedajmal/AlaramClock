package com.strong.alaramclock.Datatabase;

import android.content.Context;

import androidx.room.Room;

public class Client_Bed {

    Context context;
    static Client_Bed Object;

    //our app database object
    private final Database_Bed databaseBed;

    private Client_Bed(Context context) {
        this.context = context;
        databaseBed = Room.databaseBuilder(context, Database_Bed.class, "BED_TIME").build();
    }

    public static synchronized Client_Bed getInstance(Context context) {
        if (Object == null) {
            Object = new Client_Bed(context);
        }
        return Object;
    }

    public Database_Bed getAppDatabase() {
        return databaseBed;
    }
}
