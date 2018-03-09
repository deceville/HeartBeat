package capstone.heartbeat.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by torre on 8/15/2017.
 */

public class Activity implements Parcelable {
    public int id;
    public String Activities;
    public double METS;
    public String Intensity;
    public boolean isDone;

    protected Activity(Parcel in) {
        id = in.readInt();
        Activities = in.readString();
        METS = in.readDouble();
        Intensity = in.readString();
        isDone = in.readByte() != 0;
        weightLoss = in.readDouble();
        Equipment = in.readString();
        minutes = in.readInt();
        calories = in.readDouble();
        activities = in.createTypedArrayList(Activity.CREATOR);
        checked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(Activities);
        dest.writeDouble(METS);
        dest.writeString(Intensity);
        dest.writeByte((byte) (isDone ? 1 : 0));
        dest.writeDouble(weightLoss);
        dest.writeString(Equipment);
        dest.writeInt(minutes);
        dest.writeDouble(calories);
        dest.writeTypedList(activities);
        dest.writeByte((byte) (checked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Activity> CREATOR = new Creator<Activity>() {
        @Override
        public Activity createFromParcel(Parcel in) {
            return new Activity(in);
        }

        @Override
        public Activity[] newArray(int size) {
            return new Activity[size];
        }
    };

    public double getWeightLoss() {
        return weightLoss;
    }

    public void setWeightLoss(double weightLoss) {
        this.weightLoss = weightLoss;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }

    public double weightLoss;

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

    public Activity( String Activities, double calories){
        this.Activities = Activities;
        this.calories = calories;
    }


    public Activity(){}
}
