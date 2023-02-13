package com.strong.alaramclock.Datatabase;

import android.content.Context;

import androidx.room.Room;

public class Client_Bed {

    Context context;
    static Client_Bed bedInstance;

    //our app database object
    private final Database_Bed databaseBed;

    private Client_Bed(Context context) {
        this.context = context;
        databaseBed = Room.databaseBuilder(context, Database_Bed.class, "BED_TIME").build();
    }

    public static synchronized Client_Bed getInstance(Context context) {
        if (bedInstance == null) {
            bedInstance = new Client_Bed(context);
        }
        return bedInstance;
    }

    public Database_Bed getAppDatabase() {
        return databaseBed;
    }
}
