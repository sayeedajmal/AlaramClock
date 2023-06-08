package com.strong.alaramclock.Datatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DAO_Bed {
    @Query("SELECT * FROM Alarm_BED order by timeInMilliSec")
    List<Alarm_BED> getAll();

    @Query("DELETE FROM ALARM_BED WHERE timeInMilliSec = :TimeStamp")
    abstract void del_TimeStamp(String TimeStamp);

    @Insert
    void insert(Alarm_BED Time);

    @Delete
    void delete(Alarm_BED Time);

    @Update
    void update(Alarm_BED Time);
}
