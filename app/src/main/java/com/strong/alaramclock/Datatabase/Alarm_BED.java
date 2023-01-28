package com.strong.alaramclock.Datatabase;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Alarm_BED implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long timeInMilliSec;

    @ColumnInfo(name = "Day")
    private String Day;

    @ColumnInfo(name = "Time")
    private String Time;

    @ColumnInfo(name = "amOrPm")
    private String AmPm;

    @ColumnInfo(name = "Label")
    private String Label;

    @ColumnInfo(name = "Finished")
    private boolean Finished;

    @ColumnInfo(name = "OnOff")
    private boolean OnOff;

    /*GETTERS AND SETTERS */
    public long getTimeInMilliSec() {
        return timeInMilliSec;
    }

    public void setTimeInMilliSec(long timeInMilliSec) {
        this.timeInMilliSec = timeInMilliSec;
    }

    public String getDay() {
        return Day;
    }

    public void setDay(String day) {
        Day = day;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getAmPm() {
        return AmPm;
    }

    public void setAmPm(String amPm) {
        AmPm = amPm;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public boolean isFinished() {
        return Finished;
    }

    public void setFinished(boolean finished) {
        Finished = finished;
    }

    public boolean isOnOff() {
        return OnOff;
    }

    public void setOnOff(boolean onOff) {
        OnOff = onOff;
    }
}