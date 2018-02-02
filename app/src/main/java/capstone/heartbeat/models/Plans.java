<<<<<<< HEAD
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
    public boolean isDone;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getReduction() {
        return reduction;
    }

    public void setReduction(double reduction) {
        this.reduction = reduction;
    }

    public int freetime;
    private double calories;
    private List<Activity> activities;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Plans(String title, String date, int minutes, int freetime, double calories, List<Activity> activities, boolean done) {
        this.title = title;
        this.date = date;
        this.minutes = minutes;
        this.freetime = freetime;
        this.calories = calories;
        this.activities = activities;
        this.isDone = done;
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
=======
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
    public boolean isDone;
    public double totalWeightLoss;
    public double progress;

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public boolean isCompleted;

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public double getTotalWeightLoss() {
        return totalWeightLoss;
    }

    public void setTotalWeightLoss(double totalWeightLoss) {
        this.totalWeightLoss = totalWeightLoss;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int freetime;
    private double calories;
    private List<Activity> activities;

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Plans(String title, String date, int minutes, int freetime, double calories, List<Activity> activities, boolean done) {
        this.title = title;
        this.date = date;
        this.minutes = minutes;
        this.freetime = freetime;
        this.calories = calories;
        this.activities = activities;
        this.isDone = done;
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
>>>>>>> 44291dcba79efabc834b5e224154da19b3bd3ef9
