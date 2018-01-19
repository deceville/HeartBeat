package capstone.heartbeat.models;

import java.util.List;

/**
 * Created by Lenevo on 1/9/2018.
 */

public class Plans {

    public int id;
    private String title;
    private String date;
    public int minutes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int freetime;
    private double calories;
    private List<Activity> activities;

    public Plans(String title, String date, int minutes, int freetime, double calories, List<Activity> activities) {
        this.title = title;
        this.date = date;
        this.minutes = minutes;
        this.freetime = freetime;
        this.calories = calories;
        this.activities = activities;
    }

    public Plans() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public int getFreetime() {
        return freetime;
    }

    public void setFreetime(int freetime) {
        this.freetime = freetime;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
