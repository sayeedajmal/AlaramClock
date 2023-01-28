package com.strong.alaramclock.Datatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface roomDAO {
    @Query("SELECT * FROM Alarm_BED order by timeInMilliSec DESC")
    List<Alarm_BED> getAll();

    @Insert
    void insert(Alarm_BED Time);

    @Delete
    void delete(Alarm_BED Time);

    @Update
    void update(Alarm_BED Time);
}
