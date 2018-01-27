package capstone.heartbeat.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by torre on 8/15/2017.
 */

public class Activity {
    public int id;
    public String Activities;
    public double METS;
    public String Intensity;
    public boolean isDone;

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String Equipment;
    public int minutes;
    private double calories;
    private List<Activity> activities;

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public boolean checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getActivities() {
        return Activities;
    }

    public void setActivities(String activities) {
        Activities = activities;
    }

    public double getMETS() {
        return METS;
    }

    public void setMETS(double METS) {
        this.METS = METS;
    }

    public String getIntensity() {
        return Intensity;
    }

    public void setIntensity(String intensity) {
        Intensity = intensity;
    }

    public String getEquipment() {
        return Equipment;
    }

    public void setEquipment(String equipment) {
        Equipment = equipment;
    }

    public Activity(int id, String Activities, double METS, String Intensity, String Equipment,boolean done, int min){
        this.id = id;
        this.Activities = Activities;
        this.METS = METS;
        this.Intensity = Intensity;
        this.Equipment = Equipment;
        this.isDone = done;
        this.minutes = min;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Activity(int id, String Activities, double METS, String Intensity, String Equipment){
        this.id = id;
        this.Activities=Activities;
        this.METS = METS;
        this.Intensity = Intensity;
        this.Equipment = Equipment;
    }


    public Activity(){}
}
